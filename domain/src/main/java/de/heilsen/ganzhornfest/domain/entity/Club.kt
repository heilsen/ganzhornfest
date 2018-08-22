package de.heilsen.ganzhornfest.domain.entity

import java.util.Collections.emptyList

data class Club @JvmOverloads constructor(val name: String = "",
                                          val description: String = "",
                                          val foodList: List<Food> = emptyList<Food>(),
                                          val drinkList: List<Drink> = emptyList<Drink>(),
                                          val actionableOfferList: List<ActionableOffer> = emptyList<ActionableOffer>(),
                                          val poi: Poi = Poi(0.0, 0.0))
