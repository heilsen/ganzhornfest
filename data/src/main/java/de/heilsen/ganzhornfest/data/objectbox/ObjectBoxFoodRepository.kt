package de.heilsen.ganzhornfest.data.objectbox

import de.heilsen.ganzhornfest.domain.entity.Food
import de.heilsen.ganzhornfest.domain.repository.EntityNotFoundException
import de.heilsen.ganzhornfest.domain.repository.Repository
import io.objectbox.Box
import io.objectbox.BoxStore

class ObjectBoxFoodRepository(boxStore: BoxStore) : Repository<Food> {
    internal var box: Box<FoodEntity> = boxStore.boxFor(FoodEntity::class.java)

    internal val linsenSpaetzleSaitenwuerste = FoodEntity("Linsen & Spätzle mit Saitenwürstle")
    internal val apfelkuechle = FoodEntity("Apfehlküchle mit Zimt/Zukcer und Vanillesoße")
    internal val steakMitZwiebeln = FoodEntity("Steak mit Zwiebeln")
    internal val currywurst = FoodEntity("Currywurst")
    internal val currywurstSpezial = FoodEntity("Currywurst Spezial (mit Zwiebeln)")
    internal val roteWurst = FoodEntity("Rote Wurst vom Grill")
    internal val pommes = FoodEntity("Pommes")
    internal val arancinoSiciliano = FoodEntity("Arancino Siciliano")
    internal val arancinoMitNutella = FoodEntity("Arancino mit Nutella")
    internal val pasta = FoodEntity("Pasta")
    internal val paniniMitSalsiccia = FoodEntity("Panini mit Salsiccia")
    internal val piadinaRomana = FoodEntity("Piadina romana")
    internal val bratwurstInDerSeele = FoodEntity("1/2 Meter Bratwurst in der Seele")
    internal val crepesSuess = FoodEntity("Crêpes (süß)")
    internal val crepesPikant = FoodEntity("Crêpes (pikant)")
    internal val geraeucherteForellenMitKartoffelsalat = FoodEntity("geräucherte Forellen mit Kartoffelsalat")
    internal val lachsUndAalbroetchen = FoodEntity("Lachs- und Aalbrötchen")
    internal val shrimpsMitSosse = FoodEntity("Shrimps mit Soße")
    internal val pizza = FoodEntity("Pizza")
    internal val neckarsulmerRaedle = FoodEntity("Neckarsulmer Rädle")
    internal val schnitzelMitKartoffelsalatOderBrot = FoodEntity("Schnitzel mit Kartoffelsalat oder Brot")
    internal val wurstVomSud = FoodEntity("Wurst vom Sud")
    internal val rauchfleischbrot = FoodEntity("Rauchfleischbrot")
    internal val kraeuterkaesbrot = FoodEntity("Kräuterkäsbrot")
    internal val wurstsalat = FoodEntity("Wurstsalat")
    internal val gefuellterHals = FoodEntity("Gefüllter Hals mit Spätzle und Kartoffelsalat")
    internal val schaelrippchenMitBrot = FoodEntity("Schälrippchen mit Brot")
    internal val hacksteaks = FoodEntity("Hacksteaks")
    internal val steaks = FoodEntity("Steaks")
    internal val kartoffelsalat = FoodEntity("Kartoffelsalat")
    internal val zwiebelkuchen = FoodEntity("Zwiebelkuchen")
    internal val kuchen = FoodEntity("Kuchen")
    internal val souvlaki = FoodEntity("Souvlaki (Spieß mit Brötchen)")
    internal val langos = FoodEntity("Langos")
    internal val flammkuchen = FoodEntity("Flammkuchen")
    internal val baguettes = FoodEntity("verschiedene Baguettes")
    internal val schupfnudeln = FoodEntity("Schupfnudeln")
    internal val sauerkraut = FoodEntity("Sauerkraut")
    internal val leberUndBlutwurst = FoodEntity("Leber- und Blutwurst")
    internal val besentoast = FoodEntity("Besentoast")
    internal val kaeseUndRauchfleischbrot = FoodEntity("Käse- und Rauchfleischbrot")
    internal val kaesewuerfel = FoodEntity("Käsewürfel")
    internal val bratwuersteMitKartoffelsalat = FoodEntity("Bratwürste mit Kartoffelsalat")
    internal val waffeln = FoodEntity("Waffeln")
    internal val zuckerwatte = FoodEntity("Zuckerwatte")
    internal val suesskartoffelchips = FoodEntity("Süßkartoffelchips mit Kräuterdips")
    internal val veganeCurrywurst = FoodEntity("Vegane Currywurst mit Vollkornbrot")
    internal val veganerHotDog = FoodEntity("Veganer Hot Dog")
    internal val weizenwrapMitGegrilltemGemuese = FoodEntity("Weizenwrap mit gegrilltem Gemüse")
    internal val metBurger = FoodEntity("MET-Burger (Fleisch & veggi/vegan")
    internal val tapasTeller = FoodEntity("Tapas-Teller")
    internal val wildbratwurst = FoodEntity("Wildbratwurst aus heimischer Jagd mit Kartoffelsalat oder Brot")
    internal val baguetteMitLachs = FoodEntity("Baguette mit Lachs")
    internal val oliven = FoodEntity("Oliven")
    internal val weisswurstfruehstueck = FoodEntity("Weißwurstfrühstück")
    internal val zopf = FoodEntity("Zopf")
    internal val kartoffelpuffer = FoodEntity("Kartoffelpuffer")
    internal val maiskolben = FoodEntity("Maiskolben")
    internal val grillwurst = FoodEntity("Grillwurst")
    internal val countryPotatoes = FoodEntity("Country-Potatoes (Kartoffelspalten) mit Dip")
    internal val raclette = FoodEntity("Raclette")
    internal val gebrannteMandeln = FoodEntity("gebrannte Mandeln/Erdnüsse")
    internal val magenbrot = FoodEntity("Magenbrot")
    internal val popcorn = FoodEntity("Popcorn")
    internal val sonstigeSuessigkeiten = FoodEntity("sonstige Süßigkeiten")
    internal val haehnchen = FoodEntity("Hähnchen")
    internal val wurst = FoodEntity("Wurst")
    internal val calamaris = FoodEntity("Calamaris")
    internal val bogenwurst = FoodEntity("Bogenwurst (Bratwurstschnecke)")
    internal val snacks = FoodEntity("Snacks")
    internal val spitzdappen = FoodEntity("Seelen mit Schinken und Käse überbacken")
    internal val germknoedel = FoodEntity("Germknödel")
    internal val kaesspaetzle = FoodEntity("Kässptzle")
    internal val hamburger = FoodEntity("Hamburger")
    internal val nachosMitKaeseUeberbacken = FoodEntity("Nachos mit Käse überbacken")
    internal val thaisuppe = FoodEntity("Thaisuppe")
    internal val cheeseWaffle = FoodEntity("Cheese Waffle")
    internal val doenerKebap = FoodEntity("Döner Kebap")
    internal val pfannkuchen = FoodEntity("Pfannkuchen")
    internal val koefte = FoodEntity("Köfte", "deutsch: Frikadelle")
    internal val tuerkischeFleischGrillspezialitaeten = FoodEntity("türkische Fleisch-Grillspezialitäten")
    internal val boerek = FoodEntity("Borek", "deutsch: Blätterteigspezialiäten")
    internal val tuerkischeSuessspeisen = FoodEntity("türkische Süßspeisen aus Blätterteig")
    internal val eis = FoodEntity("Eis")
    internal val sueBoerek = FoodEntity("Sü-Börek")
    internal val sarma = FoodEntity("Sarma")
    internal val salate = FoodEntity("Salate")
    internal val muffins = FoodEntity("Muffins")
    internal val putenwiener = FoodEntity("Putenwiener")

    init {
        prefill()
    }

    override fun getAll(): List<Food> {
        return box.all.map { FoodConverter.from(it) }
    }


    override fun get(i: Int): Food {
        return FoodConverter.from(box.get(i.toLong()))
    }

    override fun get(name: String): Food {
        val entity = box.query().equal(FoodEntity_.name, name).build().findUnique() ?: throw EntityNotFoundException()
        return FoodConverter.from(entity)
    }

    fun prefill() {
        box.removeAll()
        box.put(
                linsenSpaetzleSaitenwuerste,
                apfelkuechle,
                steakMitZwiebeln,
                currywurst,
                currywurstSpezial,
                roteWurst,
                pommes,
                arancinoSiciliano,
                arancinoMitNutella,
                pasta,
                paniniMitSalsiccia,
                piadinaRomana,
                bratwurstInDerSeele,
                crepesSuess,
                crepesPikant,
                geraeucherteForellenMitKartoffelsalat,
                lachsUndAalbroetchen,
                shrimpsMitSosse,
                pizza,
                neckarsulmerRaedle,
                schnitzelMitKartoffelsalatOderBrot,
                wurstVomSud,
                rauchfleischbrot,
                kraeuterkaesbrot,
                wurstsalat,
                gefuellterHals,
                schaelrippchenMitBrot,
                hacksteaks,
                steaks,
                kartoffelsalat,
                zwiebelkuchen,
                kuchen,
                souvlaki,
                langos,
                flammkuchen,
                baguettes,
                schupfnudeln,
                sauerkraut,
                leberUndBlutwurst,
                besentoast,
                kaeseUndRauchfleischbrot,
                kaesewuerfel,
                bratwuersteMitKartoffelsalat,
                waffeln,
                zuckerwatte,
                suesskartoffelchips,
                veganeCurrywurst,
                veganerHotDog,
                weizenwrapMitGegrilltemGemuese,
                metBurger,
                tapasTeller,
                wildbratwurst,
                baguetteMitLachs,
                oliven,
                weisswurstfruehstueck,
                zopf,
                kartoffelpuffer,
                maiskolben,
                grillwurst,
                countryPotatoes,
                raclette,
                gebrannteMandeln,
                magenbrot,
                popcorn,
                sonstigeSuessigkeiten,
                haehnchen,
                wurst,
                calamaris,
                bogenwurst,
                snacks,
                spitzdappen,
                germknoedel,
                kaesspaetzle,
                hamburger,
                nachosMitKaeseUeberbacken,
                thaisuppe,
                cheeseWaffle,
                doenerKebap,
                pfannkuchen,
                koefte,
                tuerkischeFleischGrillspezialitaeten,
                boerek,
                tuerkischeSuessspeisen,
                eis,
                sueBoerek,
                sarma,
                salate,
                muffins,
                putenwiener

        )
    }

}
