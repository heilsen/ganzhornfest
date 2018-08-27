package de.heilsen.ganzhornfest.data.objectbox

import de.heilsen.ganzhornfest.domain.entity.Event
import de.heilsen.ganzhornfest.domain.repository.EntityNotFoundException
import de.heilsen.ganzhornfest.domain.repository.Repository
import io.objectbox.Box
import io.objectbox.BoxStore
import java.util.*

class ObjectBoxEventRepository(boxStore: BoxStore) : Repository<Event> {

    internal var box: Box<EventEntity> = boxStore.boxFor(EventEntity::class.java)

    val museumsplatz = "Hauptbühne Museumsplatz"
    val buehneMarktplatz = "Bühne Marktplatz"
    val marktplatz = "Marktplatz"
    val buehneKarlsplatz = "Bühne Karlsplatz"
    val karlsplatz = "Karlsplatz"
    val quergasse = "Quergasse"
    val met = "Stand Metropolitan Jazz Community"
    internal val eroeffnung = EventEntity("Eröffnung durch Oberbürgermeister Steffen Hertwig und der ehemaligen Württemberger Weinkönigin Andrea Ritz",
            Date(118, 8, 1, 16, 0).time, museumsplatz)
    internal val mvObereisesheim = EventEntity("MV Obereisesheim",
            Date(118, 8, 1, 16, 0).time, museumsplatz)
    internal val tanzgruppe = EventEntity("Tanzgruppe \"Hotsteppers\" (Neckarsulmer Sportunion)",
            Date(118, 8, 1, 18, 40).time, museumsplatz)
    internal val mgvDahenfeld = EventEntity("MGV Dahenfeld",
            Date(118, 8, 1, 20, 0).time, museumsplatz)
    internal val crazyZoo = EventEntity("Crazy Zoo", Date(118, 8, 1, 20, 0).time, buehneMarktplatz)
    internal val tlly = EventEntity("T-LLY - deutscher Hip-Hop (kreatief)", Date(118, 8, 1, 19, 0).time, buehneKarlsplatz)
    internal val new2morrow = EventEntity("New2morrow - Pop & R&B (kreatief)", Date(118, 8, 1, 19, 45).time, buehneKarlsplatz)
    internal val plannedConfusion = EventEntity("Planned Confusion - Pop & Rock (kreatief)", Date(118, 8, 1, 21, 0).time, buehneKarlsplatz)
    internal val dezent = EventEntity("DeZent - Akustik Cover (kreatief)", Date(118, 8, 1, 22, 15).time, buehneKarlsplatz)
    internal val metropolitanSwing = EventEntity("Metropolitan Swing Unit", Date(118, 8, 1, 19, 0).time, met)

    internal val karateVorfuehrung = EventEntity("Karate-Vorführung (Neckarsulmer Sport-Union)",
            Date(118, 8, 2, 13, 15).time, museumsplatz)
    internal val squaredance = EventEntity("Square Dance (Neckar-Valley-Dancers)",
            Date(118, 8, 2, 14, 0).time, museumsplatz)
    internal val blaskapelle = EventEntity("Blaskapelle der Audi Blöserphilharmonie aus Ingolstadt",
            Date(118, 8, 2, 15, 0).time, museumsplatz)
    internal val harmonikaclub = EventEntity("Harmonikaclub",
            Date(118, 8, 2, 18, 0).time, museumsplatz)
    internal val perfectHeat = EventEntity("Perfect Heat", Date(118, 8, 2, 18, 0).time, buehneMarktplatz)
    internal val gottesdienst = EventEntity("Ökumenischer Gottesdienst (bei schlechtem Wetter in der Klosterkirche)", Date(118, 8, 2, 10, 15).time, buehneKarlsplatz)
    internal val noire = EventEntity("Noire - Alternative Rock (kreatief)", Date(118, 8, 2, 19, 0).time, buehneKarlsplatz)
    internal val paradision = EventEntity("Paradision - Rock (kreatief)", Date(118, 8, 2, 19, 45).time, buehneKarlsplatz)
    internal val dreamsInstead = EventEntity("Dreams Instead - Singer/Songwriter (kreatief)", Date(118, 8, 2, 21, 0).time, buehneKarlsplatz)
    internal val jazzFrueschoppen = EventEntity("Jazziger Frühschoppen mit der Goodfellas Hot Jazz Band", Date(118, 8, 2, 11, 0).time, met)
    internal val docsBigBand = EventEntity("Docs Big Band", Date(118, 8, 2, 19, 0).time, met)
    internal val ponyreiten = EventEntity("Ponyreiten (Jugendfarm)",Date(118,8,2,16,0).time, karlsplatz)
    internal val kinderFlohmarkt = EventEntity("Kinder-Flohmarkt (Stadtverwaltung)",Date(118,8,2,16,0).time, quergasse)

    internal val polizeiBigBand = EventEntity("Polizei Big Band Heilbronn", Date(118, 8, 3, 19, 30).time, museumsplatz)
    internal val strokeUnits = EventEntity("Stroke Units", Date(118, 8, 3, 19, 0).time, buehneMarktplatz)
    internal val beautiesAndTheBeats = EventEntity("Beauties and the Beats - A Cappella Chor (kreatief)", Date(118, 8, 3, 19, 0).time, buehneMarktplatz)
    internal val annalenaUndSofie = EventEntity("Annalena & Sofie - Pop Cover (kreatief)", Date(118, 8, 3, 20, 0).time, buehneMarktplatz)
    internal val highTide = EventEntity("High Tide - Hard Rock (kreatief)", Date(118, 8, 3, 21, 15).time, buehneMarktplatz)
    internal val combinationBigBand = EventEntity("Combination Big Band", Date(118, 8, 3, 19, 0).time, met)
    internal val bastelAktion = EventEntity("Kunterbunte Spiel- und Bastelaktion (Freier Kindergarten)",Date(118,8,3,11,0).time, karlsplatz)
    internal val ponyreiten2 = EventEntity("Ponyreiten (Jugendfarm)",Date(118,8,3,11,0).time, karlsplatz)
    internal val tigermobil = EventEntity("Tigermobil (Kinder- und Jugendreferat)",Date(118,8,3,14,0).time, karlsplatz)
    internal val kasperltheater = EventEntity("Kasperltheater 'Kasperl am Strand'",Date(118,8,3,14,0).time, karlsplatz)
    internal val kasperltheater2 = EventEntity("Kasperltheater 'Kasperls Geburtstag'",Date(118,8,3,16,0).time, karlsplatz)
    internal val kinderFlohmarkt2 = EventEntity("Kinder-Flohmarkt (Stadtverwaltung)",Date(118,8,3,11,0).time, quergasse)


    init {
        prefill()
    }

    override fun getAll(): List<Event> {
        return box.all.map { EventConverter.from(it) }
    }


    override fun get(i: Int): Event {
        return EventConverter.from(box.get(i.toLong()))
    }

    override fun get(description: String): Event { //TODO: split Repository into those who can be search by names and those who can't
        val entity = box.query().equal(EventEntity_.description, description).build().findUnique()
                ?: throw EntityNotFoundException()
        return EventConverter.from(entity)
    }


    fun prefill() {
        box.removeAll()
        box.put(
                eroeffnung,
                mvObereisesheim,
                tanzgruppe,
                mgvDahenfeld, crazyZoo, tlly, new2morrow, plannedConfusion, dezent, metropolitanSwing,
                karateVorfuehrung,
                squaredance,
                blaskapelle,
                harmonikaclub,
                perfectHeat, gottesdienst, noire, paradision, dreamsInstead, jazzFrueschoppen, docsBigBand,
                polizeiBigBand, strokeUnits, beautiesAndTheBeats, annalenaUndSofie, highTide, combinationBigBand,
                ponyreiten, ponyreiten2,kinderFlohmarkt, kinderFlohmarkt2,bastelAktion,tigermobil,kasperltheater, kasperltheater2
        )
    }

}
