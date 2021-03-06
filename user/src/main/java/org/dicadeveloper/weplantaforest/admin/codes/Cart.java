package org.dicadeveloper.weplantaforest.admin.codes;

import java.beans.PropertyEditorSupport;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import org.dicadeveloper.weplantaforest.trees.User;
import org.hibernate.annotations.Cascade;
import org.springframework.hateoas.Identifiable;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class Cart implements Identifiable<Long> {

    public static class Editor extends PropertyEditorSupport {
        @Override
        public void setAsText(final String text) throws IllegalArgumentException {
        }
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "_cartId")
    private Long id;

    @Column(name = "_timeStamp")
    private Long timeStamp;

    @Enumerated(EnumType.STRING)
    @Column(name = "_cartState")
    private CartState cartState;

    @OneToMany(mappedBy = "cart", fetch = FetchType.LAZY)
    @Cascade({ org.hibernate.annotations.CascadeType.ALL })
    private List<CartItem> cartItems = new ArrayList<CartItem>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "_buyer__userId")
    private User buyer;

    @ManyToOne
    @JoinColumn(name = "_event__id")
    private Event event;

    @Column(name = "_callbackParams", length = 1024)
    private String callbackParams;

    @Column(name = "_callBackVzid", length = 32)
    private String callBackVzid;

    @Column(name = "_callBackStrasse", length = 32)
    private String callBackStrasse;

    @Column(name = "_callBackTimestamp", length = 32)
    private String callBackTimestamp;

    @Column(name = "_callBackFirma", length = 256)
    private String callBackFirma;

    @Column(name = "_callBackBanktransactionid", length = 32)
    private String callBackBanktransactionid;

    @Column(name = "_callBackVorname", length = 128)
    private String callBackVorname;

    @Column(name = "_callBackPlz", length = 16)
    private String callBackPlz;

    @Column(name = "_callBackStatus", length = 32)
    private String callBackStatus;

    @Column(name = "_callBackNachname", length = 128)
    private String callBackNachname;

    @Column(name = "_callBackOrt", length = 128)
    private String callBackOrt;

    @Column(name = "_callBackBetrag", length = 16)
    private String callBackBetrag;

    @Column(name = "_callBackLand", length = 16)
    private String callBackLand;

    @Column(name = "_callBackEmail", length = 256)
    private String callBackEmail;

    @Column(name = "_callBackTrackingcode", length = 32)
    private String callBackTrackingcode;

    @Column(name = "_callBackTransactionid", length = 64)
    private String callBackTransactionid;

    @Column(name = "_callBackOid", length = 32)
    private String callBackOid;

    @Column(name = "_callBackMethod", length = 128)
    private String callBackMethod;

    @Column(name = "_callBackZahlungsart", length = 32)
    private String callBackZahlungsart;

    @Column(name = "_callBackFirmanzusatz", length = 256)
    private String callBackFirmanzusatz;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "_abo__id", nullable = true)
    private Abo abo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "_receipt__receiptId")
    private Receipt receipt;

    @OneToOne(optional = true)
    @JoinColumn(name = "_code__id")
    private Coupon code;

    @Transient
    private boolean gift = false;

    /**
     * Target price in case this card was generated for an price based code.
     */
    @Column(name = "_targetedPrice")
    private float targetedPrice;

    public CartItem removeCartItem(final Long articleId) {
        for (final CartItem item : cartItems) {
            if (item.getPlantArticleId()
                    .equals(articleId)) {
                cartItems.remove(item);
                return item;
            }
        }
        return null;
    }

    public void addCartItem(final CartItem cartItem) {
        if (!containsCartItem(cartItem)) {
            cartItems.add(cartItem);
        }
    }

    public boolean containsCartItem(final CartItem cartItem) {
        for (final CartItem item : cartItems) {
            if (item.getPlantArticleId()
                    .equals(cartItem.getPlantArticleId())) {
                return true;
            }
        }
        return false;
    }

    public void removeCartItem(final CartItem cartItem) {
        cartItems.remove(cartItem);
    }

    public boolean isFilled() {
        return !cartItems.isEmpty();
    }

    public Date getTimeStampAsDate() {
        return new Date(timeStamp);
    }

    @Transient
    public int getTreeCount() {
        int count = 0;
        for (final CartItem item : cartItems) {
            count += item.getAmount();
        }
        return count;
    }

    @Transient
    public BigDecimal getTotalPrice() {
        BigDecimal total = new BigDecimal(0.00);
        for (final CartItem item : cartItems) {
            total = total.add(item.getTotalPrice());
        }
        return total;
    }

    @Transient
    public List<Long> getPlantArticleIds() {
        final List<Long> ids = new ArrayList<Long>();
        for (final CartItem item : cartItems) {
            ids.add(item.getPlantArticleId());
        }
        return ids;
    }

    public Date getCallBackTimestampAsDate() {
        if (callBackTimestamp == null) {
            return null;
        }
        return new Date(Long.parseLong(callBackTimestamp) * 1000); // its php,
                                                                   // sec since
                                                                   // 1970, not
                                                                   // msec
    }
}
