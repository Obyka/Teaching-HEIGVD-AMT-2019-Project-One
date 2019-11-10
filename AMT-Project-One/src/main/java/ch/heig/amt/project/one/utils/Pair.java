package ch.heig.amt.project.one.utils;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * Classe utilitaire permettant de construire des pairs génériques
 * @param <L> type de la valeur de gauche
 * @param <R> type de la valeur de droite
 */
@Getter
@Setter
@EqualsAndHashCode
public class Pair<L,R> {
    private L key;
    private R value;

    public Pair(L key, R value) {
        this.key = key;
        this.value = value;
    }
}
