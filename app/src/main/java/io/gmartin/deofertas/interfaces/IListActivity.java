package io.gmartin.deofertas.interfaces;

import java.util.List;

import io.gmartin.deofertas.models.Offer;

/**
 * Created by guille on 17/02/18.
 */

public interface IListActivity {
    List<Offer> getOfferList();

    Boolean getIsPort();
}
