# checkout

This is a Checkout system with the main logic implemented at `Checkout`.

The checkout can have N `Item` added with the `scan(Item item)`method. 

The checkout is configured with N `Discount` when it is instantiated with `new Checkout(discountList)`.

By calling the `total()` method you should be able to get the sum of the price of the
items, minus any discounts configured in the checkout.

## Data

Product code | Name | Price
------------ | ------------- | -------------
001 | Travel Card Holder | £9.25
002 | Personalised cufflinks | £45.00
003 | Kids T-shirt | £19.95

## Discounts
If you spend over £60, then you get 10% off your purchase. 
Implemented at `XPercentOffAfterYPoundsDiscount`

If you buy 2 or more travel card holders then the price drops to
£8.50.
Implemented at `XPoundsAfterYProductsDiscount`

The discounts were implemented in a way so that you can customize 
when it will be activated and the behaviour based on the rules stated above.

For example if instead of £60 I want to activate on £100, or if I want to give 40% off,
it is possible to create a discount scheme with this configuration.

Same if I want to change the price of a given product if there are X of them in the basket.


## Test Cases

>Basket: 001,002,003
>
>Total price expected: £66.78

>Basket: 001,003,001
>
>Total price expected: £36.95

>Basket: 001,002,001,003
>
>Total price expected: £73.76


## Build

`gradlew clean build`
