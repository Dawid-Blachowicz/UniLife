package com.blach.unilife.model.data.expenses

import androidx.compose.ui.graphics.Color

enum class ExpenseCategory() {
    HOME_AND_BILLS,
    FOOD,
    HEALTH_AND_BEAUTY,
    ENTERTAINMENT_AND_TRAVEL,
    CLOTHES_AND_ACCESSORIES,
    EDUCATION,
    TRANSPORT,
    OTHER
}

val categoryColors = mapOf(
    ExpenseCategory.HOME_AND_BILLS to 0xFFE57373.toInt(),
    ExpenseCategory.FOOD to 0xFFFFD54F.toInt(),
    ExpenseCategory.HEALTH_AND_BEAUTY to 0xFFBA68C8.toInt(),
    ExpenseCategory.ENTERTAINMENT_AND_TRAVEL to 0xFF4FC3F7.toInt(),
    ExpenseCategory.CLOTHES_AND_ACCESSORIES to 0xFFAED581.toInt(),
    ExpenseCategory.EDUCATION to 0xFFFF8A65.toInt(),
    ExpenseCategory.TRANSPORT to 0xFF90A4AE.toInt(),
    ExpenseCategory.OTHER to 0xFF90EE90.toInt()
)

val categoryDisplayNames = mapOf(
    ExpenseCategory.HOME_AND_BILLS to "Dom i rachunki",
    ExpenseCategory.FOOD to "Jedzenie",
    ExpenseCategory.HEALTH_AND_BEAUTY to "Zdrowie i uroda",
    ExpenseCategory.ENTERTAINMENT_AND_TRAVEL to "Rozrywka i podróże",
    ExpenseCategory.CLOTHES_AND_ACCESSORIES to "Odzież i akcesoria",
    ExpenseCategory.EDUCATION to "Edukacja",
    ExpenseCategory.TRANSPORT to "Transport",
    ExpenseCategory.OTHER to "Inne"
)

fun getCategoryColor(category: ExpenseCategory): Color {
    return Color(categoryColors[category] ?: 0xFF000000.toInt())
}

fun getCategoryDisplayName(category: ExpenseCategory): String {
    return categoryDisplayNames[category] ?: "Inne"
}
