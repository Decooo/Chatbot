package com.jakub.chatbot.service;

import com.jakub.chatbot.entity.Rating;
import com.jakub.chatbot.exceptions.NotFoundException;
import com.jakub.chatbot.repository.MovieRepository;
import com.jakub.chatbot.repository.RatingRepository;
import com.jakub.chatbot.util.SentimentAnalysis;
import com.jakub.chatbot.util.WitRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

@Service
public class MovieService {

	MovieRepository movieRepository;
	RatingRepository ratingRepository;

	public MovieService(MovieRepository movieRepository, RatingRepository ratingRepository) {
		this.movieRepository = movieRepository;
		this.ratingRepository = ratingRepository;
	}

	public ModelAndView listMovies() {
		var model = new ModelAndView("listMovies.html");
		model.addObject("movies", movieRepository.findAll());
		return model;
	}

	public ModelAndView getMovie(int idMovie) throws NotFoundException {
		var model = new ModelAndView("movie.html");
		var movie = movieRepository.findById(idMovie);
		if (!movie.isPresent()) throw new NotFoundException("Nie znaleziono filmu!");
		var ratings = ratingRepository.findByMovie(movie.get());

		model.addObject("movie", movie.get());
		model.addObject("ratings", ratings);
		return model;
	}

	public ModelAndView markingMovie(int idMovie) throws NotFoundException, IOException {
		var model = new ModelAndView("marking.html");
		var movie = movieRepository.findById(idMovie);
		if (!movie.isPresent()) throw new NotFoundException("Nie znaleziono filmu!");

		SentimentAnalysis sentimentAnalysis = new SentimentAnalysis();
		//var result = sentimentAnalysis.analysis("to film na 8-9*, bo i świetne zdjęcia (wielka inwencja operatora), i {dobry} montaż, czyli znakomite wyczucie kiedy ciąć, sceny prawdziwie filmowe, niewymagające dużo dialogów, bo tak są mocne - np. scena z trumną, kapitalne przeplatanie wizji z realnością na końcu, wątek walki duchowej z odbiciem w lustrze (raz to anioł, raz diabeł) - reżyser świetnie wymyslił mnóstwo scen, sa treściwe, plastyczne, pełne znaczenia, zaciekawiające (popychające akcję do przodu i wzmacniające emocje)! Naprawdę, w bardzo realny świat nas wprowadził, przekonał do niego że wreszcie nie patrzyłem zezem by wyłuskać słabości warsztatowe czy artystyczne...");
		var result = sentimentAnalysis.analysis("Łaska fana na pstrym koniu jeździ. Po drugim \"Pitbullu\" Patryk Vega był tytułowany Midasem polskiej kinematografii. Teraz stał się jej Edem Woodem. Wcześniej słyszałem, że znalazł klucz do serc i portfeli masowego widza. Dziś, że brutalnie gwałci jego inteligencję oraz wrażliwość. \"Pitbulle\" nazywano mocnym kinem rozrywkowym, \"Botoks\" to zastrzyk z kwasu żenady i nieudolności. Cóż, najnowsze dzieło twórcy \"Służb specjalnych\" z pewnością nie jest dobrym filmem. Z drugiej strony nie potrafię dostrzec artystycznej przepaści, która rzekomo dzieli \"Botoks\" oraz, dajmy na to, \"Niebezpieczne kobiety\". Oba tytuły zostały przecież zrealizowane według identycznego \"Vegańskiego\" przepisu: zbierz wszystkie najbardziej hardcore'owe anegdoty zasłyszane od przedstawicieli danego zawodu, połącz je pretekstową intrygą, podlej wulgarnym humorem, a na końcu opatrz demaskatorską formułką \"inspirowane autentycznymi zdarzeniami\". Do tej pory powyższa instrukcja obsługi działała niezawodnie. W miniony piątek nastąpił wysyp reklamacji. \n" +
				"Trudno mi uwierzyć, że upodobania polskiej publiczności gwałtownie się zmieniły i wszyscy dostrzegli nagle mroczne oblicze Patryka V. Mało prawdopodobne wydaje się również, że służba zdrowia cieszy się w Polsce tak dobrą opinią, że jej przejaskrawiony, tabloidowy portret z \"Botoksu\" wywołał w widzach święte oburzenie. Złość – i to jak najbardziej uzasadnioną – może jednak budzić zrodzony w najniższym kręgu piekła pomysł, by na ekranie zmiksować ze sobą dramat aborcyjny i rubaszną czarną komedię. Gdy w jednej scenie reżyser epatuje cię obrazem dogorywającego płodu, a w kolejnej próbuje rozbawić gagiem o perwersyjnej staruszce, która umieściła w waginie siedem mandarynek, czujesz się, jakby ktoś chciał ci wysadzić w powietrze mózg. To trudne do opisania uczucie powraca w trakcie seansu kilka razy, ale na szczęście szybko mija. Karuzela zdarzeń kręci się bowiem w takim tempie, że prawdziwa konsternacja i osłupienie przychodzą dopiero po seansie. \n" +
				"Co ciekawe, tym razem reżyser z lepszym skutkiem niż w \"Pitbullach\" prowadzi wielowątkową opowieść. Śledząc losy czterech bohaterek, Vega bierze pod lupę kolejne obszary medycznego podwórka. Podąża z kamerą za załogą karetki, ginekologami, chirurgami plastycznymi, a także przedstawicielami koncernów farmaceutycznych. Fabularne nitki układają się zarówno w szokujący obraz przeżartego przez korupcję i cynizm środowiska lekarskiego, jak i opowieść o silnych kobietach zmuszonych rozpychać się łokciami w męskim świecie. W scenariuszu nie brakuje dziur fabularnych oraz cudownych zbiegów okoliczności, ale względny ciąg przyczynowo-skutkowy udało się jakoś zachować. Pierwsze pół godziny filmu wydaje się co prawda zbiorem bulwersujących skeczy, ale z czasem narracja łapie właściwy rytm i nabiera przejrzystości. O ile np. w \"Niebezpiecznych kobietach\" trudno było połapać się w kulisach wielkiego przekrętu paliwowego, tutaj Vega klarownie tłumaczy machlojki producentów leków oraz nieprawidłowości w funkcjonowaniu szpitali. Mimo wszystko radziłbym nie traktować \"Botoksu\" jako kompendium wiedzy o rodzimej służbie zdrowia. W optyce reżysera każda incydentalna patologia wydaje się bowiem normą, a jedno zgniłe jabłko – całym sadem. Oceńcie sami, na ile szkodliwe społecznie jest takie podejście do palącego tematu. \n" +
				"Twórczości Vegi można zarzucić sporo: realizacyjny pośpiech i niechlujność, zamiłowanie do przesady, nierówne prowadzenie aktorów oraz dresiarską mentalność. Twórcy \"Taśm grozy\" jedno trzeba jednak przyznać: kręci filmy w pełni autorskie. Na dobre i na złe. Otacza się stałymi współpracownikami, sprawuje kontrolę nad każdym najdrobniejszym aspektem swoich obrazów i ma łatwo rozpoznawalny charakter pisma. W cierpiącym na deficyt osobowości polskim kinie rozrywkowym Vega jest zjawiskiem i powszechnie rozpoznawalną marką. Marki mają zaś to do siebie, że doskonale wiesz, czego się po nich spodziewać. Słowem, kupujesz albo dziękujesz. ");
		System.out.println("result = " + result);

		model.addObject("movie", movie.get());
		model.addObject("rating", new Rating());
		return model;
	}

	public ModelAndView sendMarking(Rating rating, int idMovie) {
		var model = new ModelAndView("redirect:/movie/list/" + idMovie + "/marking");

		var witRequest = new WitRequest();
		witRequest.doRequest(rating.getContentRating());

		System.out.println("Treść wiadomości: " + rating.getContentRating());
		rating.setContentRating("");
		return model;
	}
}
