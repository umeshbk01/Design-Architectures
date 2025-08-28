package ConcurrentBooking.services;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import ConcurrentBooking.constants.SeatAvailability;
import ConcurrentBooking.modals.Seat;
import ConcurrentBooking.modals.Show;
import ConcurrentBooking.util.Hold;

public class SeatService {
    private final Map<String, Show> shows;
    private final Map<String, Hold> holds = new ConcurrentHashMap<>();
    private final ScheduledExecutorService schedular = Executors.newScheduledThreadPool(2);

    private final long holdTtl;

    public SeatService(Map<String, Show> shows, long holdTtl) {
        this.shows = shows;
        this.holdTtl = holdTtl;
    }

    public Optional<String> holdSeats(String showId, String userId, Set<String> seatIds) {
        Show show = shows.get(showId);
        if (show == null) return Optional.empty();

        boolean lock =false;
        try {
            lock = show.showLock.tryLock(2, TimeUnit.SECONDS);
            if(!lock){
                System.out.println("[SeatService] Couldn't acquire lock for show " + showId);
                return Optional.empty();
            }
            for(String seatId: seatIds){
                Seat s = show.seatMap.get(seatId);
                if( s == null){
                    System.out.println("[SeatService] Seat " + seatId + " doesn't exist");
                    return Optional.empty();
                }
                if(s.state != SeatAvailability.AVAILABLE){
                    System.out.println("[SeatService] Seat " + seatId + " not available (state=" + s.state + ")");
                    return Optional.empty();
                }
            }
            for (String sid : seatIds) {
                show.seatMap.get(sid).state = SeatAvailability.RESERVED;
            }
            String holdId = "hold-" + UUID.randomUUID();
            long expiry = System.currentTimeMillis() + holdTtl * 1000;
            Hold hold = new Hold(holdId, showId, userId, seatIds, expiry);
            holds.put(holdId, hold);
            schedular.schedule(() -> {
                releaseHoldIfExpired(holdId);
            }, holdTtl, TimeUnit.SECONDS);

            System.out.println("[SeatService] Held seats " + seatIds + " for user " + userId + " holdId=" + holdId);
            return Optional.of(holdId);
        } catch (InterruptedException e) {
            // TODO: handle exception
            Thread.currentThread().interrupt();
            return Optional.empty();
        }finally{
            if(lock){
                show.showLock.unlock();
            }
        }
    }

    public boolean releaseHold(String holdId){
        Hold hold = holds.get(holdId);
        if(hold == null) return false;
        Show show = shows.get(hold.showId);
        if (show == null) return false;
        boolean locked = false;
        try {
            locked = show.showLock.tryLock(2, TimeUnit.SECONDS);
            if (!locked) return false;
            for (String sid : hold.seatIds) {
                Seat s = show.seatMap.get(sid);
                if (s != null && s.state == SeatAvailability.RESERVED) {
                    s.state = SeatAvailability.AVAILABLE;
                }
            }
            holds.remove(holdId);
            System.out.println("[SeatService] Released hold " + holdId + " seats " + hold.seatIds);
            return true;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return false;
        } finally {
            if (locked) {
                show.showLock.unlock();
            }
        }
    }

    public boolean confirmHold(String holdId){
        Hold hold = holds.get(holdId);
        if(hold == null) return false;
        Show show = shows.get(hold.showId);
        if(show == null) return false;
        boolean locked = false;
        try {
            locked = show.showLock.tryLock(2, TimeUnit.SECONDS);
            if(!locked) return false;
            for (String sid : hold.seatIds) {
                Seat s = show.seatMap.get(sid);
                if (s == null || s.state != SeatAvailability.RESERVED) {
                    System.out.println("[SeatService] Confirm failed: seat " + sid + " state=" + (s==null?"null":s.state));
                    return false;
                }
            }
            for(String sid: hold.seatIds){
                show.seatMap.get(sid).state = SeatAvailability.BOOKED;
            }
            holds.remove(holdId);
            System.out.println("[SeatService] Confirmed hold " + holdId + " => seats booked: " + hold.seatIds);
            return true;
        } catch (InterruptedException e) {
            // TODO: handle exception
            Thread.currentThread().interrupt();
            return false;
        }finally{
            if(locked){
                show.showLock.unlock();
            }
        }
    }

    public void releaseHoldIfExpired(String holdId){
         Hold hold = holds.get(holdId);
        if (hold == null) return;
        if (System.currentTimeMillis() >= hold.expiryEpochMillis) {
            System.out.println("[SeatService] Hold expired, releasing: " + holdId);
            releaseHold(holdId);
        }
    }

    public Map<String, Seat> getSeatMap(String showId) {
        Show show = shows.get(showId);
        if (show == null) return Collections.emptyMap();
        return show.seatMap;
    }

    public void shutdown() {
        schedular.shutdownNow();
    }
}
