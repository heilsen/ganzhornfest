package de.heilsen.ganzhornfest.data.objectbox

import de.heilsen.ganzhornfest.domain.entity.GeoLocation
import de.heilsen.ganzhornfest.domain.repository.EntityNotFoundException
import de.heilsen.ganzhornfest.domain.repository.Repository
import io.objectbox.Box
import io.objectbox.BoxStore

class ObjectBoxGeoLocationRepository(boxStore: BoxStore) : Repository<GeoLocation> {
    private var box: Box<GeoLocationEntity> = boxStore.boxFor(GeoLocationEntity::class.java)

    val asb = GeoLocationEntity(49.19229, 9.223065)
    val bsv = GeoLocationEntity(49.191768, 9.224544)
    val dlrg1 = GeoLocationEntity(49.191662, 9.224517)
    val dlrg2 = GeoLocationEntity(49.1916077, 9.2226579)
    val downtownboys = GeoLocationEntity(49.191618, 9.225298)
    val drk = GeoLocationEntity(49.191957, 9.222933)
    val fsv = GeoLocationEntity(49.192041, 9.222668)
    val freierKindergartenNeckarsulm = GeoLocationEntity(49.19111, 9.222178)
    val georgspfadfinder = GeoLocationEntity(49.192071, 9.223429)
    val concordia = GeoLocationEntity(49.192345, 9.222834)
    val lassallia = GeoLocationEntity(49.191917, 9.22237)
    val griechischeGemeinde = GeoLocationEntity(49.191196, 9.2225)
    val harmonikaClub = GeoLocationEntity(49.191778, 9.222837)
    val Jugendfarm1 = GeoLocationEntity(49.191543, 9.225009)
    val Jugendfarm2 = GeoLocationEntity(49.19111, 9.222048)
    val jU = GeoLocationEntity(49.191687, 9.2223)
    val Jusos = GeoLocationEntity(49.191517, 9.222594)
    val kirchenchor = GeoLocationEntity(49.191805, 9.224282)
    val kiwanis = GeoLocationEntity(49.191779, 9.224976)
    val kolpingBlasorchester = GeoLocationEntity(49.1914438, 9.222666)
    val Kolpingsfamilie = GeoLocationEntity(49.192164, 9.22317)
    val KolpingJugend = GeoLocationEntity(49.192052, 9.2229932)
    val MetropolitanJazzCommunity = GeoLocationEntity(49.191443, 9.224773)
    val NSUFussball = GeoLocationEntity(49.1915472, 9.2233634)
    val NSUHandball1 = GeoLocationEntity(49.1917453, 9.2253106)
    val NSUHandball2 = GeoLocationEntity(49.1917584, 9.2252637)
    val NSUHandball3 = GeoLocationEntity(49.1917856, 9.2251376)
    val NSUKanu = GeoLocationEntity(49.191452, 9.222574)
    val NSUKarate = GeoLocationEntity(49.191638, 9.222142)
    val NSULeichtathletik = GeoLocationEntity(49.191584, 9.225269)
    val NSURugby = GeoLocationEntity(49.191691, 9.222882)
    val NSUTischtennis1 = GeoLocationEntity(49.191682, 9.224902)
    val NSUTischtennis2 = GeoLocationEntity(49.191566, 9.222624)
    val Tierschutzverein = GeoLocationEntity(49.191835, 9.224575)
    val Saengerbund = GeoLocationEntity(49.192232, 9.222689)
    val SCAmorbach = GeoLocationEntity(49.192161, 9.22278)
    val Schuetzengilde1 = GeoLocationEntity(49.191691, 9.222882)
    val Schuetzengilde2 = GeoLocationEntity(49.1915875, 9.2227491)
    val SPDOrtsverein = GeoLocationEntity(49.192001, 9.22296)
    val StoneHeads = GeoLocationEntity(49.191693, 9.222072)
    val StPaulusClub = GeoLocationEntity(49.191992, 9.223657)
    val Sulmanafetza = GeoLocationEntity(49.191405, 9.22337)
    val TauchclubWalhai = GeoLocationEntity(49.191856, 9.222512)
    val TCSulmtal1 = GeoLocationEntity(49.191428, 9.223834)
    val TCSulmtal2 = GeoLocationEntity(49.1913447, 9.2237053)
    val TuerksporNeckarsulm = GeoLocationEntity(49.191808, 9.222764)
    val TuerksporNeckarsulmJugend = GeoLocationEntity(49.19185, 9.222794)
    val UFC = GeoLocationEntity(49.191459, 9.223019)
    val WaldkindergartenWaldzauber1 = GeoLocationEntity(49.191715, 9.224514)
    val WaldkindergartenWaldzauber2 = GeoLocationEntity(49.191197, 9.222202)
    val Weinbauverein = GeoLocationEntity(49.191996, 9.222762)
    /*
49.191853   9.224752    Karussell                      playground
49.192436   9.223148    Eisenbähnle                    playground
49.191764   9.223357    Kinder-Flohmarkt               playground
49.191197   9.222202    Waldkindergarten               playground
49.191756   9.225421    WC                             toilets
49.192685   9.222914    WC                             toilets
49.192106   9.222767    WC                             toilets
49.19146    9.221921    WC                             toilets
49.191238   9.222961    WC                             toilets
49.191036   9.222433    WC                             toilets
49.192083   9.222574    Museumsplatz                   stage
49.191665   9.225433    Marktplatz                     stage
49.191405   9.222139
Karlsplatz                    stage
49.19146    9.2248      Metropolitan Jazz Community    stage
49.192608   9.223059    Erste-Hilfe (ASB)              first_aid
49.193846   9.227222    ZOB (Ballei)                   busstop

     */

    init {
        prefill()
    }

    private fun prefill() {
        box.removeAll()
        box.put(asb)
        box.put(bsv)
        box.put(dlrg1)
        box.put(dlrg2)
        box.put(downtownboys)
        box.put(drk)
        box.put(fsv)
        box.put(freierKindergartenNeckarsulm)
        box.put(georgspfadfinder)
        box.put(concordia)
        box.put(lassallia)
        box.put(griechischeGemeinde)
        box.put(harmonikaClub)
        box.put(Jugendfarm1)
        box.put(Jugendfarm2)
        box.put(jU)
        box.put(Jusos)
        box.put(kirchenchor)
        box.put(kiwanis)
        box.put(kolpingBlasorchester)
        box.put(Kolpingsfamilie)
        box.put(KolpingJugend)
        box.put(MetropolitanJazzCommunity)
        box.put(NSUFussball)
        box.put(NSUHandball1)
        box.put(NSUHandball2)
        box.put(NSUHandball3)
        box.put(NSUKanu)
        box.put(NSUKarate)
        box.put(NSULeichtathletik)
        box.put(NSURugby)
        box.put(NSUTischtennis1)
        box.put(NSUTischtennis2)
        box.put(Tierschutzverein)
        box.put(Saengerbund)
        box.put(SCAmorbach)
        box.put(Schuetzengilde1)
        box.put(Schuetzengilde2)
        box.put(SPDOrtsverein)
        box.put(StoneHeads)
        box.put(StPaulusClub)
        box.put(Sulmanafetza)
        box.put(TauchclubWalhai)
        box.put(TCSulmtal1)
        box.put(TCSulmtal2)
        box.put(TuerksporNeckarsulm)
        box.put(TuerksporNeckarsulmJugend)
        box.put(UFC)
        box.put(WaldkindergartenWaldzauber1)
        box.put(WaldkindergartenWaldzauber2)
        box.put(Weinbauverein)

    }

    override fun getAll(): List<GeoLocation> {
        return box.all.map { geoLocationEntity : GeoLocationEntity -> GeolocationConverter.from(geoLocationEntity) }
    }

    override fun get(i: Int): GeoLocation {
        return GeolocationConverter.from(box.get(i.toLong()))
    }

    @Throws(EntityNotFoundException::class)
    override fun get(name: String): GeoLocation {
        throw EntityNotFoundException()
    }
}
