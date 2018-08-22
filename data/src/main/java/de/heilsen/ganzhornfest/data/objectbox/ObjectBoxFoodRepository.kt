package de.heilsen.ganzhornfest.data.objectbox

import de.heilsen.ganzhornfest.domain.entity.Food
import de.heilsen.ganzhornfest.domain.repository.EntityNotFoundException
import de.heilsen.ganzhornfest.domain.repository.Repository
import io.objectbox.Box
import io.objectbox.BoxStore

class ObjectBoxFoodRepository(boxStore: BoxStore) : Repository<Food> {
    internal var box: Box<FoodEntity> = boxStore.boxFor(FoodEntity::class.java)

    internal var linsenSpaetzleSaitenwuerste = FoodEntity("Linsen & Spätzle mit Saitenwürstle")
    internal var apfelkuechle = FoodEntity("Apfehlküchle mit Zimt/Zukcer und Vanillesoße")
    internal var steakMitZwiebeln = FoodEntity("Steak mit Zwiebeln")
    internal var currywurst = FoodEntity("Currywurst")
    internal var currywurstSpezial = FoodEntity("Currywurst Spezial (mit Zwiebeln)")
    internal var roteWurst = FoodEntity("Rote Wurst vom Grill")
    internal var pommes = FoodEntity("Pommes")
    internal var arancinoSiciliano = FoodEntity("Arancino Siciliano")
    internal var arancinoMitNutella = FoodEntity("Arancino mit Nutella")
    internal var pasta = FoodEntity("Pasta")
    internal var paniniMitSalsiccia = FoodEntity("Panini mit Salsiccia")
    internal var piadinaRomana = FoodEntity("Piadina romana")
    internal var bratwurstInDerSeele = FoodEntity("1/2 Meter Bratwurst in der Seele")
    internal var crepesSuess = FoodEntity("Crêpes (süß)")
    internal var crepesPikant = FoodEntity("Crêpes (pikant)")
    internal var geraeucherteForellenMitKartoffelsalat = FoodEntity("geräucherte Forellen mit Kartoffelsalat")
    internal var lachsUndAalbroetchen = FoodEntity("Lachs- und Aalbrötchen")
    internal var shrimpsMitSosse = FoodEntity("Shrimps mit Soße")
    internal var pizza = FoodEntity("Pizza")
    internal var neckarsulmerRaedle = FoodEntity("Neckarsulmer Rädle")
    internal var schnitzelMitKartoffelsalatOderBrot = FoodEntity("Schnitzel mit Kartoffelsalat oder Brot")
    internal var wurstVomSud = FoodEntity("Wurst vom Sud")
    internal var rauchfleischbrot = FoodEntity("Rauchfleischbrot")
    internal var kraeuterkaesbrot = FoodEntity("Kräuterkäsbrot")
    internal var wurstsalat = FoodEntity("Wurstsalat")
    internal var gefuellterHals = FoodEntity("Gefüllter Hals mit Spätzle und Kartoffelsalat")
    internal var schaelrippchenMitBrot = FoodEntity("Schälrippchen mit Brot")
    internal var hacksteaks = FoodEntity("Hacksteaks")
    internal var steaks = FoodEntity("Steaks")
    internal var kartoffelsalat = FoodEntity("Kartoffelsalat")
    internal var zwiebelkuchen = FoodEntity("Zwiebelkuchen")
    internal var kuchen = FoodEntity("Kuchen")
    internal var souvlaki = FoodEntity("Souvlaki (Spieß mit Brötchen)")
    internal var langos = FoodEntity("Langos")
    internal var flammkuchen = FoodEntity("Flammkuchen")
    internal var baguettes = FoodEntity("verschiedene Baguettes")
    internal var schupfnudeln = FoodEntity("Schupfnudeln")
    internal var sauerkraut = FoodEntity("Sauerkraut")
    internal var leberUndBlutwurst = FoodEntity("Leber- und Blutwurst")
    internal var besentoast = FoodEntity("Besentoast")
    internal var kaeseUndRauchfleischbrot = FoodEntity("Käse- und Rauchfleischbrot")
    internal var kaesewuerfel = FoodEntity("Käsewürfel")
    internal var bratwuersteMitKartoffelsalat = FoodEntity("Bratwürste mit Kartoffelsalat")
    internal var waffeln = FoodEntity("Waffeln")
    internal var zuckerwatte = FoodEntity("Zuckerwatte")
    internal var suesskartoffelchips = FoodEntity("Süßkartoffelchips mit Kräuterdips")
    internal var veganeCurrywurst = FoodEntity("Vegane Currywurst mit Vollkornbrot")
    internal var veganerHotDog = FoodEntity("Veganer Hot Dog")
    internal var weizenwrapMitGegrilltemGemuese = FoodEntity("Weizenwrap mit gegrilltem Gemüse")
    internal var metBurger = FoodEntity("MET-Burger (Fleisch & veggi/vegan")
    internal var tapasTeller = FoodEntity("Tapas-Teller")
    internal var wildbratwurst = FoodEntity("Wildbratwurst aus heimischer Jagd mit Kartoffelsalat oder Brot")
    internal var baguetteMitLachs = FoodEntity("Baguette mit Lachs")
    internal var oliven = FoodEntity("Oliven")
    internal var weisswurstfruehstueck = FoodEntity("Weißwurstfrühstück")
    internal var zopf = FoodEntity("Zopf")
    internal var kartoffelpuffer = FoodEntity("Kartoffelpuffer")
    internal var maiskolben = FoodEntity("Maiskolben")
    internal var grillwurst = FoodEntity("Grillwurst")
    internal var countryPotatoes = FoodEntity("Country-Potatoes (Kartoffelspalten) mit Dip")
    internal var raclette = FoodEntity("Raclette")
    internal var gebrannteMandeln = FoodEntity("gebrannte Mandeln/Erdnüsse")
    internal var magenbrot = FoodEntity("Magenbrot")
    internal var popcorn = FoodEntity("Popcorn")
    internal var sonstigeSuessigkeiten = FoodEntity("sonstige Süßigkeiten")
    internal var haehnchen = FoodEntity("Hähnchen")
    internal var wurst = FoodEntity("Wurst")
    internal var calamaris = FoodEntity("Calamaris")
    internal var bogenwurst = FoodEntity("Bogenwurst (Bratwurstschnecke)")
    internal var snacks = FoodEntity("Snacks")
    internal var spitzdappen = FoodEntity("Seelen mit Schinken und Käse überbacken")
    internal var germknoedel = FoodEntity("Germknödel")
    internal var kaesspaetzle = FoodEntity("Kässptzle")
    internal var hamburger = FoodEntity("Hamburger")
    internal var nachosMitKaeseUeberbacken = FoodEntity("Nachos mit Käse überbacken")
    internal var thaisuppe = FoodEntity("Thaisuppe")
    internal var cheeseWaffle = FoodEntity("Cheese Waffle")
    internal var doenerKebap = FoodEntity("Döner Kebap")
    internal var pfannkuchen = FoodEntity("Pfannkuchen")
    internal var koefte = FoodEntity("Köfte", "deutsch: Frikadelle")
    internal var tuerkischeFleischGrillspezialitaeten = FoodEntity("türkische Fleisch-Grillspezialitäten")
    internal var boerek = FoodEntity("Borek", "deutsch: Blätterteigspezialiäten")
    internal var tuerkischeSuessspeisen = FoodEntity("türkische Süßspeisen aus Blätterteig")
    internal var eis = FoodEntity("Eis")
    internal var sueBoerek = FoodEntity("Sü-Börek")
    internal var sarma = FoodEntity("Sarma")
    internal var salate = FoodEntity("Salate")
    internal var muffins = FoodEntity("Muffins")
    internal var putenwiener = FoodEntity("Putenwiener")

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
