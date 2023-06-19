import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDate;

@Entity
public class PlayerCategory {
    private long id;
    private Player player;
    private ChessCategory category;
    private LocalDate receiveDate;

    public PlayerCategory() {
    }

    public PlayerCategory(Player player, ChessCategory category, LocalDate receiveDate) {
        this.player = player;
        this.category = category;
        this.receiveDate = receiveDate;
        this.player.addPlayerCategory(this);
    }

    public PlayerCategory(ChessCategory category, LocalDate receiveDate) {

        this.category = category;
        this.receiveDate = receiveDate;
    }

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    public long getId() {
        return id;
    }

    @ManyToOne
    public Player getPlayer() {
        return player;
    }

    @ManyToOne
    public ChessCategory getCategory() {
        return category;
    }

    @Basic(optional = false)
    public LocalDate getReceiveDate() {
        return receiveDate;
    }

    private void setId(long id) {
        this.id = id;
    }

    public void setReceiveDate(LocalDate receiveDate) {
        this.receiveDate = receiveDate;
    }

    public void setPlayer(Player player) {

        if (this.player != null) {
            this.player.removePlayerCategory(this);
            this.player = player;
            this.player.addPlayerCategory(this);
        } else {
            this.player = player;
        }
    }

    public void setCategory(ChessCategory category) {
        if (this.category == category) return;
        if (this.category != null) {
            this.category.removePlayerCategory(this);
            this.category = null;
        }
        this.category = category;
        category.addPlayerCategory(this);
    }
}


