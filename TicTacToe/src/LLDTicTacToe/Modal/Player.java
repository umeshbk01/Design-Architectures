package LLDTicTacToe.Modal;

public class Player {
    private String userName;
    private PlayingPieces playingPiece;

    public Player(String name, PlayingPieces playingPiece){
        this.userName = name;
        this.playingPiece = playingPiece;
    }

    public String getUserName(){
        return userName;
    }

    public void setUserName(String userName){
        this.userName = userName;
    }

    public PlayingPieces getPlayingPieces(){
        return playingPiece;
    }

    public void setPlayingPieces(PlayingPieces playingPiece){
        this.playingPiece = playingPiece;
    }

}
