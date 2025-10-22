package ru.reddmix;

import java.math.BigDecimal;

public class Game {
    private Long playerId;
    private BigDecimal betAmount;
    private BigDecimal winAmount;

    // Геттеры и сеттеры
    public Long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(Long playerId) {
        this.playerId = playerId;
    }

    public BigDecimal getBetAmount() {
        return betAmount;
    }

    public void setBetAmount(BigDecimal betAmount) {
        this.betAmount = betAmount;
    }

    public BigDecimal getWinAmount() {
        return winAmount;
    }

    public void setWinAmount(BigDecimal winAmount) {
        this.winAmount = winAmount;
    }

    @Override
    public String toString() {
        return "Game{" +
                "playerId=" + playerId +
                ", betAmount=" + betAmount +
                ", winAmount=" + winAmount +
                '}';
    }
}
