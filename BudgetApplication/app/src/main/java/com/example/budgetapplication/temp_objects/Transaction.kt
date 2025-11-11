package com.example.budgetapplication.temp_objects

data class Transaction (
    var merchantName: String = "",
    var transactionTotal: Double = 0.0,
    var lineItems: MutableList<Line_Item> = mutableListOf()
){
    fun addTransactions() {
        lineItems.clear()
        merchantName = "Comedy Center"
        transactionTotal = 1103.46
        lineItems.add(Line_Item("orangutans", 25.05))
        lineItems.add(Line_Item("Breakfast Cereals", 25.05))
        lineItems.add(Line_Item("Bad Jokes", 50.15))
        lineItems.add(Line_Item("Banana Subscription Box", 15.99))
        lineItems.add(Line_Item("Mismatched Socks", 8.50))
        lineItems.add(Line_Item("Inflatable Keyboard", 32.00))
        lineItems.add(Line_Item("Left-Handed Coffee Mug", 12.25))
        lineItems.add(Line_Item("Philosophical Pancakes", 9.75))
        lineItems.add(Line_Item("Surprise Box of Regrets", 49.99))
        lineItems.add(Line_Item("Used Time Machine (No Refunds)", 299.95))
        lineItems.add(Line_Item("Invisible Umbrella", 18.40))
        lineItems.add(Line_Item("Premium Dad Jokes Expansion Pack", 22.00))
        lineItems.add(Line_Item("Emergency Disco Ball", 45.25))
        lineItems.add(Line_Item("Pet Rock Deluxe Edition", 10.00))
        lineItems.add(Line_Item("Certified Unicorn Dust", 125.75))
        lineItems.add(Line_Item("Quantum Toaster", 85.50))
        lineItems.add(Line_Item("Suspiciously Cheap Jetpack", 199.99))
        lineItems.add(Line_Item("Interdimensional Sunglasses", 67.89))

    }



}