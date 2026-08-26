package de.heilsen.ganzhornfest.search

fun formatClubList(
    clubs: String,
    manyClubsLabel: String,
    maxNamedClubs: Int = 2,
): String {
    val names = clubs.split(',').map { it.trim() }.filter { it.isNotEmpty() }
    return if (names.size > maxNamedClubs) manyClubsLabel else clubs
}
