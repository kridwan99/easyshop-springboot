package org.yearup.data;

import org.yearup.models.ShoppingCart;

public interface ShoppingCartDao
{
    // ✅ Match the method name in MySqlShoppingCartDao
    ShoppingCart getByUserId(int userId);

    // ✅ Add to cart (used in ShoppingCartController)
    void addToCart(int userId, int productId);

    // ✅ Update quantity of a specific product in cart
    void updateQuantity(int userId, int productId, int quantity);

    // ✅ Clear all items from a user's cart
    void clearCart(int userId);
}
