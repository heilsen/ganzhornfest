package de.heilsen.ganzhornfest.info

data class InfoModel(
    // null until the DB flow's first emission. countClubs() runs on Dispatchers.IO and can lag
    // behind first composition, especially right after a fresh install reseeds the database, so
    // this stays null rather than a 0 that would flash "0 Neckarsulmer Vereine" before the real
    // count arrives.
    val clubCount: Int?,
)
