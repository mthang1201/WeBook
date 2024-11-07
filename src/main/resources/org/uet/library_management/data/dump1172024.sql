-- MySQL dump 10.13  Distrib 8.0.38, for Win64 (x86_64)
--
-- Host: localhost    Database: library_db
-- ------------------------------------------------------
-- Server version	8.0.39

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `bookmarks`
--

DROP TABLE IF EXISTS `bookmarks`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `bookmarks` (
  `userId` int NOT NULL,
  `isbn13` varchar(255) DEFAULT NULL,
  KEY `userId` (`userId`),
  CONSTRAINT `bookmarks_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `users` (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bookmarks`
--

LOCK TABLES `bookmarks` WRITE;
/*!40000 ALTER TABLE `bookmarks` DISABLE KEYS */;
INSERT INTO `bookmarks` VALUES (12,'9781685798147'),(12,'9781685798147'),(12,'9781107632349'),(12,'9781471109416');
/*!40000 ALTER TABLE `bookmarks` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `books`
--

DROP TABLE IF EXISTS `books`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `books` (
  `documentId` int NOT NULL AUTO_INCREMENT,
  `title` varchar(255) NOT NULL,
  `authors` text,
  `publisher` varchar(255) DEFAULT NULL,
  `publishedDate` varchar(255) DEFAULT NULL,
  `description` text,
  `isbn10` varchar(20) DEFAULT NULL,
  `isbn13` varchar(20) DEFAULT NULL,
  `pageCount` int DEFAULT NULL,
  `categories` text,
  `averageRating` decimal(3,2) DEFAULT NULL,
  `ratingsCount` int DEFAULT NULL,
  `imageLinks` text,
  `language` varchar(10) DEFAULT NULL,
  `maturityRating` varchar(50) DEFAULT NULL,
  `printType` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`documentId`),
  UNIQUE KEY `isbn13` (`isbn13`)
) ENGINE=InnoDB AUTO_INCREMENT=873 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `books`
--

LOCK TABLES `books` WRITE;
/*!40000 ALTER TABLE `books` DISABLE KEYS */;
INSERT INTO `books` VALUES (6,'Classroom of the Elite (Manga) Vol. 3','Syougo Kinugasa, Yuyu Ichino','Seven Seas Entertainment','2022-08-16','Students of the prestigious Tokyo Metropolitan Advanced Nurturing High School are given remarkable freedom--if they can win, barter, or save enough points to work their way up the ranks! After getting in a fight with students from Class C, Sudou is on the verge of expulsion. Ayanokouji and Horikita may have found a witness in Class D\'s Sakura, but she\'s reluctant to make waves and get involved. And even if she did, would anyone trust the word of a Class D student? Meanwhile, Ayanokouji and the others discover additional ways to earn points outside of test scores. But will it be enough to buy their way out of Class D? Or is the system truly rigged against them?','1685798144','9781685798147',0,'Comics & Graphic Novels',0.00,158,'http://books.google.com/books/content?id=f7Z9EAAAQBAJ&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api&fife=w800','en','NOT_MATURE','BOOK'),(7,'Classroom of the Elite (Light Novel) Vol. 1','Syougo Kinugasa','Seven Seas Entertainment','2019-02-07','\'\"Students of the prestigious Tokyo Metropolitan Advanced Nurturing High School are given remarkable freedom--if they can win, barter, or save enough points to work their way up the ranks! Ayanokoji Kiyotaka has landed at the bottom in the scorned Class D, where he meets Horikita Suzune, who\'\'s determined to rise up the ladder to Class A. Can they beat the system in a school where cutthroat competition is the name of the game? \"\'','1645051544','9781645051541',0,'Young Adult Fiction',0.00,312,'http://books.google.com/books/content?id=XdqEDwAAQBAJ&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api&fife=w800','en','NOT_MATURE','BOOK'),(8,'Classroom of the Elite (Light Novel) Vol. 4','Syougo Kinugasa','Seven Seas Entertainment','2019-11-21','The second half of the special summer test finds the students duking it out aboard a luxurious cruise ship. Divided into twelve groups patterned after the signs of the Zodiac, all four classes are faced with a devilishly difficult test of their thinking skills. They may have survived the island, but they\'re not out of hot water yet!','1645051579','9781645051572',0,'Young Adult Fiction',0.00,308,'http://books.google.com/books/content?id=jNSyDwAAQBAJ&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api&fife=w800','en','NOT_MATURE','BOOK'),(9,'Classroom of the Elite (Manga) Vol. 8','Syougo Kinugasa, Yuyu Ichino','Seven Seas Entertainment','2023-11-07','Students of the prestigious Tokyo Metropolitan Advanced Nurturing High School are given remarkable freedom--if they can win, barter, or save enough points to work their way up the ranks! Class D barely got to celebrate their win on the island before they were given another special test, with way more points at stake. The catch is, they have to work together with the other classes. No small feat considering Class D can barely cooperate with each other. They\'ve also made an enemy of Class C, and Ryuuen is cooking up a new plot to topple Ayanokouji and Horikita. Can they outmaneuver him, or have they met their match?',NULL,'9798891603226',0,'Comics & Graphic Novels',0.00,166,'http://books.google.com/books/content?id=QyPcEAAAQBAJ&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api&fife=w800','en','NOT_MATURE','BOOK'),(10,'Classroom of the Elite (Light Novel) Vol. 7','Syougo Kinugasa','Seven Seas Entertainment','2020-10-22','The end of the second semester is near, and Ryuuen\'s manhunt for Class D\'s mastermind is only getting more aggressive. When he and his goons decide to torture an answer out of Karuizawa, Ayanokouji decides it\'s finally time to step forward--if, that is, Karuizawa doesn\'t break under the pressure first!','1645053938','9781645053934',0,'Young Adult Fiction',0.00,248,'http://books.google.com/books/content?id=VND9DwAAQBAJ&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api&fife=w800','en','NOT_MATURE','BOOK'),(11,'Classroom of the Elite: Year 2 (Light Novel) Vol. 5','Syougo Kinugasa, Tomoseshunsaku','Seven Seas Entertainment','2023-05-18','As the second semester begins, two additional events are announced: the school\'s sports festival and its first-ever cultural festival! But before these two can kick off, an unexpected special exam will be held. The rules seem simple, but the questions might be heavier than anyone expects... Are the students truly ready to cast their final votes?',NULL,'9798888437391',0,'Comics & Graphic Novels',0.00,366,'http://books.google.com/books/content?id=7gu6EAAAQBAJ&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api&fife=w800','en','NOT_MATURE','BOOK'),(18,'Classroom of the Elite','Syougo Kinugasa, Tomoseshunsaku','Không có nhà xuất bản','2022','Không có mô tả',NULL,NULL,0,'Không có thể loại',0.00,0,'https://via.placeholder.com/150','en','NOT_MATURE','BOOK'),(19,'Classroom of the Elite: Year 2 (Light Novel) Vol. 6','Syougo Kinugasa, Tomoseshunsaku','Seven Seas Entertainment','2023-06-15','The results of the Unanimous Special Exam weigh heavily on Ayanokouji\'s class. Kushida, Hasebe, and Wang haven\'t come to school in days. But time marches on, and when details of the Sports Festival are announced, Horikata\'s opponents disrupt the planning meeting. The class is now behind before the games can even begin! Can everyone live with the choices they\'ve made and come out on top?',NULL,'9798888438343',0,'Comics & Graphic Novels',0.00,366,'http://books.google.com/books/content?id=Ic6_EAAAQBAJ&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api&fife=w800','en','NOT_MATURE','BOOK'),(20,'Classroom of the Elite (Light Novel) Vol. 10','Syougo Kinugasa, Tomoseshunsaku','Seven Seas Entertainment','2022-02-17','It\'s spring, and for the first time in the school\'s history, no one has been expelled after the third semester exams. As a result, the Advanced Nurturing High School sets a cruel test--each class must choose one of their own members to be expelled. Chaos consumes the first-years as Hirata tries and fails to keep the class from turning on each other, Ichinose strikes a costly bargain with Nagumo, and Ryuuen\'s classmates seem ready to throw him to the wolves. Can Class C make it out of this unscathed--or will they be undone by traitors within?','1648279775','9781648279775',0,'Young Adult Fiction',0.00,325,'http://books.google.com/books/content?id=yIFeEAAAQBAJ&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api&fife=w800','en','NOT_MATURE','BOOK'),(21,'Classroom of the Elite: Year 2 (Light Novel) Vol. 2','Syougo Kinugasa, Tomoseshunsaku','Seven Seas Entertainment','2022-07-21','Ayanokouji\'s relationship with Karuizawa deepens, while the aftershock of his perfect mathematics score ripples through the school. Horikita asks to join the student council, and is accepted by Nagumo. And summer vacation brings with it no rest, but another special exam--a reprise of the earlier deserted island test. Except this time, it\'ll be a battle royale with all three grade levels duking it out against each other!','1685794483','9781685794484',0,'Comics & Graphic Novels',0.00,379,'http://books.google.com/books/content?id=qmh3EAAAQBAJ&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api&fife=w800','en','NOT_MATURE','BOOK'),(53,'Wor(l)d Religions','Daniel Deleanu','iUniverse','2003-12-16','Daniel Deleanu continues in Wor(l)d Religions the logosophistic adventure started in Principles of Logosophism, The Logoarchetype and Logosophistic Investigations. Unlike the other three books from the logosophistic series, Wor(l)d Religions appeals to a wider array of readers. Daniel Deleanu chooses a rational methodology, namely that exposed by Immanuel Kant in his essay Fundamental Principals of the Metaphysic of Morals. By this approach, the creator of logosophism does not intend to reduce the study of religion to a strict framework of empirical determinism, but, on the contrary, he wishes to prove that the analytical study religion should have all the attributes of science. By eliminating most of the subjective properties of world religions, which are due to the nature of the observer rather than the religions themselves, the author traces a common path of all major beliefs, leading to the most important archetype of humankind, the Word (the logoarchetype), which is associated with the Divinity in every spiritual tradition.','1469784246','9781469784243',0,'Religion',0.00,121,'http://books.google.com/books/content?id=JHenBAAAQBAJ&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api&fife=w800','en','NOT_MATURE','BOOK'),(55,'A Theory of Semiotics','Umberto Eco','Indiana University Press','1979','\" . . . the greatest contribution to [semiotics] since the pioneering work of C. S. Peirce and Charles Morris.\" —Journal of Aesthetics and Art Criticism \" . . . draws on philosophy, linguistics, sociology, anthropology and aesthetics and refers to a wide range of scholarship . . . raises many fascinating questions.\" —Language in Society \" . . . a major contribution to the field of semiotic studies.\" —Robert Scholes, Journal of Aesthetics and Art Criticism \" . . . the most significant text on the subject published in the English language that I know of.\" —Arthur Asa Berger, Journal of Communication Eco\'s treatment demonstrates his mastery of the field of semiotics. It focuses on the twin problems of the doctrine of signs—communication and signification—and offers a highly original theory of sign production, including a carefully wrought typology of signs and modes of production.','0253202175','9780253202178',3,'Language Arts & Disciplines',4.50,372,'http://books.google.com/books/content?id=BoXO4ItsuaMC&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api&fife=w800','en','NOT_MATURE','BOOK'),(56,'Legal Education and the Reproduction of Hierarchy','Duncan Kennedy','NYU Press','2004-07-01','In 1983 Harvard law professor Duncan Kennedy self-published a biting critique of the law school system called Legal Education and the Reproduction of Hierarchy. This controversial booklet was reviewed in several major law journals—unprecedented for a self-published work—and influenced a generation of law students and teachers. In this well-known critique, Duncan Kennedy argues that legal education reinforces class, race, and gender inequality in our society. However, Kennedy proposes a radical egalitarian alternative vision of what legal education should become, and a strategy, starting from the anarchist idea of workplace organizing, for struggle in that direction. Legal Education and the Reproduction of Hierarchy is comprehensive, covering everything about law school from the first day to moot court to job placement to life after law school. Kennedy\'s book remains one of the most cited works on American legal education. The visually striking original text is reprinted here, making it available to a new generation. The text is buttressed by commentaries by five prominent legal scholars who consider its meaning for today, as well as by an introduction and afterword by the author that describes the context in which Kennedy wrote the book, including a brief history of critical legal studies.','0814748228','9780814748220',0,'Law',0.00,231,'http://books.google.com/books/content?id=gDehBwAAQBAJ&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api&fife=w800','en','NOT_MATURE','BOOK'),(59,'A Concise History of Greece','Richard Clogg','Cambridge University Press','2002-06-20','This book provides a concise, illustrated introduction to the history of modern Greece, with a new final chapter about Greek history and politics to the present day. 56 illustrations. 10 maps.','0521004799','9780521004794',4,'History',3.00,316,'http://books.google.com/books/content?id=H5pyUIY4THYC&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api&fife=w800','en','NOT_MATURE','BOOK'),(60,'Damascus','Ross Burns','Psychology Press','2005','Lavishly illustrated, the first book in English to relate the history of Damascus, is a compelling and unique exploration of a fascinating city.','0415271053','9780415271059',0,'Damascus (Syria)',0.00,409,'http://books.google.com/books/content?id=SVR2cjWptCAC&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api&fife=w800','en','NOT_MATURE','BOOK'),(61,'Images of Nebuchadnezzar','Ronald Herbert Sack','Susquehanna University Press','2004','Images of Nebuchadnezzar attempts to probe the diversity of cultural attitudes reflected in the characterizations of this famous king through an examination of both the original cuneiform sources as well as the accounts of chronographers written in Greek, Roman, and medieval times. Included in this revised and expanded second edition are two new chapters that examine both Nebuchadnezzar\'s administrative policies and the impact that his death had on both contemporary and later cultures. Both the positive and negative images of the king are explored, with conclusions being developed as to what the authors of the various surviving accounts actually thought the king really was. In the process, the whole nature of historiography in the ancient world is analyzed, and a number of broad conclusions are developed. Anyone who has ever read Second Kings or the books of Daniel and Jeremiah of the Old Testament is familiar with the name of Nebuchadnezzar, the king of Babylon who conquered the kingdom of Judah and destroyed Solomon\'s temple. As the second member of the Chaldean dynasty of Mesopotamia (626-539 B.C.), he ruled for forty-three years (605-562 B.C.), during which time he also led military campaigns into Syria and Lebanon. He also organized a number of building projects that were to transform Babylon into one of the seven wonders of the ancient world. Among his noteworthy achievements were the construction of massive fortification walls around Babylon, the refurbishing of Marduk\'s temple in the city, and the building of huge palaces that served as the king\'s residences. Tales of these legendary achievements, as well as those of his father, Nabopolassar (626-605 B.C.), also found their way into the narratives of a number of Greek, Roman, and medieval historians and chronographers many centuries later. Unfortunately, much of the record of Nebuchadnezzar\'s achievements that was written in his own time has not survived. Instead, only secondary accounts of his military campaigns or his construction projects in Babylon written in Greek, Latin, Hebrew, or Arabic are available for analysis. These stories vary greatly in content and emphasis and, in many cases, distort much of what we know from Nebuchadnezzar\'s own sources. The Hebrews, for example, described Nebuchadnezzar\'s siege of Jerusalem in such a way as to consider it something that should never again be repeated. The Greeks, on the other hand, saw the building projects in Babylon as evidence of almost superhuman achievements, as monuments that were the result of efforts by a king who was almost godlike. Why, then, is there such diversity in the characterizations of Nebuchadnezzar? This book proposes answers to these questions.','1575910799','9781575910796',0,'Biography & Autobiography',0.00,202,'http://books.google.com/books/content?id=nxC1wF3_IEAC&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api&fife=w800','en','NOT_MATURE','BOOK'),(62,'Georgina i la Dragona','Không có tác giả','Không có nhà xuất bản','Không có ngày xuất bản','Không có mô tả','8448852656','9788448852658',0,'Comics & Graphic Novels',0.00,48,'https://via.placeholder.com/150','ca','NOT_MATURE','BOOK'),(63,'Escuela de dragones 3 - El secreto de la dragona del agua','Tracey West','MOLINO','2023-01-19','¿TE APUNTAS A LA ESCUELA DE DRAGONES? A Drake le encanta volar a lomos de su dragón y entrenar con sus amigos Rori, Bo y Ana, pero cuando alguien intenta robar la mágica Piedra de Dragón que les ayuda en sus aventuras, ¡el grupo de amigos deberá lanzarse a la acción! Aunque Bo ha empezado a comportarse de una forma muy rara... ¿Tendrá algún secreto? ¿Y estará relacionado con el misterioso ladrón? En esta serie encontrarás: - Historias dirigidas a lectores que empiezan a leer de manera independiente. - Textos sencillos, contenidos interesantes, tramas ágiles e ilustraciones en todas las páginas. - Narraciones ideales para estimular la confianza y la seguridad en sí mismos de los nuevos lectores.','8427232926','9788427232921',0,'Juvenile Fiction',0.00,95,'http://books.google.com/books/content?id=s2iXEAAAQBAJ&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api&fife=w800','es','NOT_MATURE','BOOK'),(64,'Emma. La petita dragona d\'Orient','Không có tác giả','Không có nhà xuất bản','Không có ngày xuất bản','Không có mô tả','8413034019','9788413034010',0,'Không có thể loại',0.00,0,'https://via.placeholder.com/150','ca','NOT_MATURE','BOOK'),(66,'Escuela de dragones 2 - El rescate de la dragona del sol','Tracey West','MOLINO','2022-09-08','¿TE APUNTAS A LA ESCUELA DE DRAGONES? Drake y sus amigos Ana, Rori y Bo entrenan dragones para el rey. Cuando Kepri, la dragona de Ana, enferma, deberán trabajar todos juntos para salvarla. ¿Fue la magia oscura lo que la hizo enfermar? ¿Tiene Lombriz un nuevo poder secreto que podría ayudarla? Los maestros de dragones están a punto de averiguarlo. En esta serie encontrarás: -Historias dirigidas a lectores que empiezan a leer de manera independiente. -Textos sencillos, contenidos interesantes, tramas ágiles e ilustraciones en todas las páginas. -Narraciones ideales para estimular la confianza y la seguridad en sí mismos de los nuevos lectores.','8427227760','9788427227767',0,'Juvenile Fiction',0.00,100,'http://books.google.com/books/content?id=Ohh1EAAAQBAJ&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api&fife=w800','es','NOT_MATURE','BOOK'),(68,'Valentina Y La Dragona / Valentina and the Dragon','Lucia Serrano','Beascoa','2019-06-25','¡Una nueva versión de la leyenda de Sant Jordi! Tenéis en vuestras manos una leyenda que ha permanecido en el olvido a lo largo de los tiempos: La verdadera historia de Valentina y la Dragona. Abrid bien vuestros ojos y oídos. Cerrad la boca, que no entren moscas. Y preparaos para conocer los detalles de cómo la terrible Dragona aterrorizó a un pueblo entero. Cómo estuvo a punto de merendarse al delicado y bello príncipe para saciar su hambre voraz. Y cómo Valentina, la más valiente entre las valientes, solucionó todo este desaguisado con arrojo, fiereza, y humor. Mucho humor. Porque tal vez las cosas no son como nos las habían contado. Y tal vez las rosas fueron blancas. O amarillas. ENGLISH DESCRIPTION A new version of the legend of Saint Jordi! You have in your hands a legend that has been forgotten over time: The true history of Valentina and the Dragon. Open your eyes and ears. Close your mouth or you\'ll just catch flies. And get ready to learn the details about how the terrible Dragon terrorized an entire town. How she was about to snack on the delicate, handsome prince to satisfy her voracious hunger. And how Valentina, the bravest of the brave, solved the entire situation with courage, ferocity, and humor. Lots of humor. Because maybe things didn\'t happen the way they told us they did. And maybe the roses were white. Or yellow.','8448852664','9788448852665',0,'Juvenile Fiction',0.00,48,'http://books.google.com/books/content?id=U35awgEACAAJ&printsec=frontcover&img=1&zoom=1&source=gbs_api&fife=w800','es','NOT_MATURE','BOOK'),(72,'Dragon Ball','Akira Toriyama','Viz Media','2000','The quiet life of a young monkey-tailed boy named Goku is disrupted when he meets the boy-crazy Bulma, who is on a quest to collect seven Dragon Balls. If she succeeds, the Eternal Dragon will grant her one wish. Illustrations.','1569314950','9781569314951',2,'Comics & Graphic Novels',5.00,194,'http://books.google.com/books/content?id=3ZaknseaTG4C&printsec=frontcover&img=1&zoom=1&source=gbs_api&fife=w800','en','NOT_MATURE','BOOK'),(73,'Dragon Ball Z','Akira Toriyama','Viz Graphic Novels','2002','Gohan, Kuririn, and Bulma struggle to save their Dragon Balls from Captain Ginyu.','1569316988','9781569316986',1,'Comics & Graphic Novels',5.00,196,'http://books.google.com/books/content?id=Wu9QF8rm7WUC&printsec=frontcover&img=1&zoom=1&source=gbs_api&fife=w800','en','NOT_MATURE','BOOK'),(74,'Dragon Ball Super, Vol. 16','Akira Toriyama','VIZ Media LLC','2022-08-02','Granolah is the last of the Cerealians, a people who were all but wiped out by the Saiyans and Freeza’s army many years ago. When he learns that the long-lost twin to the Dragon Ball that the old Namekian Monaito keeps in their home has been found, Granolah steals it and makes a wish that will allow him to start his quest for revenge against the Saiyans—to become the strongest being in the whole universe! Meanwhile, the Heeters work behind the scenes to put Granolah out of his misery once and for all...by enlisting Goku and Vegeta’s help! -- VIZ Media','1974733572','9781974733576',0,'Comics & Graphic Novels',0.00,193,'http://books.google.com/books/content?id=IllJEAAAQBAJ&printsec=frontcover&img=1&zoom=1&source=gbs_api&fife=w800','en','NOT_MATURE','BOOK'),(75,'Dragon Soul','Derek Padula','Derek Padula','2015-06-02','With a foreword from Christopher Sabat and Sean Schemmel, Dragon Soul: 30 Years of Dragon Ball Fandom is a grand celebration of the world\'s greatest anime and manga and it\'s momentous 30th Anniversary. Join me on a global adventure in search of the 7 dragon balls, as we head west toward Japan, the birthplace of Dragon Ball. Along the way we\'ll meet 81 fans from 25 countries who will share their Dragon Ball story. From artists to authors, collectors to philosophers, we\'ll hear their Dragon Soul and discover how Dragon Ball changed their lives. Includes over 100 images. We\'ll meet such famous fans as Lawrence Simpson (MasakoX) from Team Four Star, Malik from Dragon Ball New Age, Salagir from Dragon Ball Multiverse, MMA fighter Marcus Brimage, YouTube celebrities SSJGoshin4, Nelson Junior (Casa do Kame), and film critic Chris Stuckmann, famous cosplayers \"Living Ichigo,\" Atara Collis, and Jah\'lon Escudero, the creators of Dragon Ball Z: Light of Hope, Twitter star @Goku, authors Patrick Galbraith, Nestor Rubio, and Vicente Ramirez, and dozens more. Joining us will be 27 professionals from 7 countries, including American voice actors Chris Sabat (Vegeta), Sean Schemmel (Goku), Chris Ayres (Freeza), Chris Rager (Mister Satan), Mike McFarland (Master Roshi), Chuck Huber (Android 17), Kyle Hebert (Son Gohan), Jason Douglas (Beerus), Chris Cason (Tenshinhan), FUNimation employees Justin Rojas, Adam Sheehan, and Rick Villa, Dragon Ball Z composer Bruce Faulconer, Dragon Ball manga editor Jason Thompson, Canadian voice actors Peter Kelamis (Goku) and Brian Drummond (Vegeta), Latin American voice actors Mario Castaneda (Goku), Rene Garcia (Vegeta), Eduardo Garza (Krillin), French voice actor Eric Legrand (Vegeta), French journalist Olivier Richard, Spanish voice actors Jose Antonio Gavira (Goku), Julia Oliva (Chichi), and manga editor David Hernando, Danish voice actors Caspar Philllipson (Goku) and Peter Secher Schmidt (Freeza), and Brazilian voice actor Wendel Bezerra (Goku). Gather your belongings, jump on your magic cloud, and embark on a grand adventure, in Dragon Soul: 30 Years of Dragon Ball Fandom!','1943149011','9781943149018',0,'Comics & Graphic Novels',0.00,816,'http://books.google.com/books/content?id=92nDCQAAQBAJ&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api&fife=w800','en','NOT_MATURE','BOOK'),(76,'Dragon Ball Culture Volume 1','Derek Padula','Derek Padula','2014-12-21','See Dragon Ball with new eyes. This book is your cultural tour guide of Dragon Ball, the world’s most recognized anime and manga series. Over 11 years in development, at over 2,000 pages, and featuring over 1,800 unique terms, Dragon Ball Culture is a 7 Volume analysis of your favorite series. You will go on an adventure with Son Goku, from Chapter 1 to 194 of the original Dragon Ball series, as we explore every page, every panel, and every sentence, to reveal the hidden symbolism and deeper meaning of Dragon Ball. In Volume 1 you will discover the origin of Dragon Ball. How does Akira Toriyama get his big break and become a manga author? Why does he make Dragon Ball? Where does Dragon Ball’s culture come from? And why is it so successful? Along the way you’ll be informed, entertained, and inspired. You will learn more about your favorite series and about yourself. Now step with me through the doorway of Dragon Ball Culture.','0983120579','9780983120575',1,'Comics & Graphic Novels',5.00,248,'http://books.google.com/books/content?id=JYvmBQAAQBAJ&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api&fife=w800','en','NOT_MATURE','BOOK'),(77,'Dragon Ball Culture Volume 6','Derek Padula','Derek Padula','2016-05-19','Goku meets god! In Dragon Ball Culture Volume 6, we’ll reunite with Goku as he ascends to heaven, trains with Kami for 3 years, and battles the reincarnated Demon King Pikkoro! When Goku reaches heaven he is greeted by the always-controversial Mister Popo. But who is Mister Popo, and why does he look so strange? His ancient cultural origin will finally be revealed! From there we’ll explore Kami’s roots in Japanese Shinto and Chinese Buddhism. You’ll discover how Kami and Pikkoro are related on a spiritual level, how reincarnation works within the Dragon World, and what it means for the new demon king to be the ‘son of the father who was cast down from heaven.’ Afterward, we’ll enter the 23rd Tenkaichi Budokai! But will Goku’s friends recognize him, and will he be strong enough to persevere?! Who is this green-skinned man who calls himself “Ma Junia,” and why is he such a grave threat to Goku and the world?! Discover the amazing truth behind these new characters, with surprising mystery’s and reveals from your old friends, as we take a cultural tour through the final volume of the original Dragon Ball manga! It’s a battle of life and death, and Goku’s the only one who can save us!! Volume 6 explores Chapters 162 to 194 of the Dragon Ball manga. It’s time to face god!','1943149070','9781943149070',0,'Comics & Graphic Novels',0.00,320,'http://books.google.com/books/content?id=UOgvDAAAQBAJ&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api&fife=w800','en','NOT_MATURE','BOOK'),(78,'Dragon Ball, Vol. 8','Akira Toriyama','VIZ Media LLC','2003-07-08','Eath\'s greatest hero...is from outerspace! Dragon Ball introduces a young monkey-tailed boy named Goku (a wry update of the classic Chinese \"Monkey King\" legend), whose quiet life changes when he meets a girl named Bulma who is on a quest to collect seven \"Dragon Balls.\" If she gathers them all, an incredibly powerful dragon will appear and grant her one wish. But the precious orbs are scattered all over the world, and Bulma could use the help of a certain super-strong boy... (In Japan, Dragon Ball and Dragon Ball Z were originally a single 42-volume series. VIZ Media\'s Dragon Ball contains vols. 1-16 of the original Japanese Dragon Ball, from the beginning of the series to the climax of Goku\'s last fight with Piccolo.) Tired of losing their best operatives to Son Goku, the commanders of the Red Ribbon Army hire Taopaipai, the world\'s greatest assassin, to \"take care\" of him permanently! To have a chance of defeating this new opponent, Goku must climb the miles-high Karin Tower, where a mysterious hermit guards a jug of magic water which will grant the one who drinks it super strength. And while Goku struggles to get the magic water, time is running out...because Commander Red only needs two more Dragon Balls to make his deepest, darkest wishes come true!','1569319278','9781569319277',1,'Comics & Graphic Novels',3.00,194,'http://books.google.com/books/content?id=HFYjkib95zkC&printsec=frontcover&img=1&zoom=1&source=gbs_api&fife=w800','en','NOT_MATURE','BOOK'),(79,'Dragon Ball, Vol. 10','Akira Toriyama','VIZ Media LLC','2003-07-01','Eath\'s greatest hero...is from outerspace! Dragon Ball introduces a young monkey-tailed boy named Goku (a wry update of the classic Chinese \"Monkey King\" legend), whose quiet life changes when he meets a girl named Bulma who is on a quest to collect seven \"Dragon Balls.\" If she gathers them all, an incredibly powerful dragon will appear and grant her one wish. But the precious orbs are scattered all over the world, and Bulma could use the help of a certain super-strong boy... (In Japan, Dragon Ball and Dragon Ball Z were originally a single 42-volume series. VIZ Media\'s Dragon Ball contains vols. 1-16 of the original Japanese Dragon Ball, from the beginning of the series to the climax of Goku\'s last fight with Piccolo.) Goku needs just one more Dragon Ball to wish Upa\'s father back to life...but the Ball is in the hands of an old enemy! Then, Goku and his friends part ways, promising to meet again in three years at the Tenka\'ichi Budôkai, the \"Strongest-Under-the-Heavens\" Martial Arts Tournament. But this time the competition is stronger than ever: Tenshinhan and Chaozu, the deadly disciples of Tsuru-Sen\'nin, the Crane Hermit! Will Kame-Sen\'nin\'s turtle-style kung fu beat crane style? Or will their strange new opponents be triumphant?','1569319294','9781569319291',1,'Comics & Graphic Novels',3.00,196,'http://books.google.com/books/content?id=7EZpuenjnewC&printsec=frontcover&img=1&zoom=1&source=gbs_api&fife=w800','en','NOT_MATURE','BOOK'),(80,'Dragon Ball Culture Volume 2','Derek Padula','Derek Padula','2015-01-14','Son Goku\'s adventure begins! Join me in Dragon Ball Culture Volume 2, as we travel along with Goku on his epic journey for greater strength. With each step that Goku takes, you\'ll discover more of the hidden spirituality and symbolism in Dragon Ball that makes the series so successful. You\'ll see how author Akira Toriyama synthesizes Chinese culture, Western technology, and Buddho-Daoist philosophy to create a series that speaks to your humanity. Not because of the action or the humor, but because it reminds you of what it means to be alive. Along the way you\'ll learn of Goku\'s ancient origin. You\'ll hear how the legend of a wild monkey-man begins in India, evolves across 2,000 years of Chinese and Japanese history, and leads to the Goku you know and love. I\'ll walk you through the journey from the first page to the last. And by the time we\'re done, you will be an expert on Dragon Ball\'s culture. Volume 2 explores Chapters 1 to 23 of the Dragon Ball manga. So let\'s take our first step with Goku!','0983120544','9780983120544',0,'Comics & Graphic Novels',0.00,507,'http://books.google.com/books/content?id=uxcqBgAAQBAJ&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api&fife=w800','en','NOT_MATURE','BOOK'),(81,'Dragon Ball Culture Volume 3','Derek Padula','Derek Padula','2015-02-06','Goku begins a new adventure, and this time the quest is to conquer thyself. In Dragon Ball Culture Volume 3: Battle, you’ll discover the origin of Goku’s training mentality. You’ll see how Akira Toriyama combines thousands of years of martial arts history and modern cinema together to create the Tenkaichi Budōkai. And you’ll hear how Dragon Ball almost gets cancelled, but then changes its format to become the world’s most recognized anime and manga series. Travel alongside Goku as he becomes the disciple of the world’s greatest martial artist, meets his new training partner, and competes in the largest tournament on Earth. Will this wild monkey boy gain the discipline he needs to become the champion? Volume 3 explores Chapters 24 to 53 of the Dragon Ball manga. Let the battle begin!','0983120501','9780983120506',0,'Comics & Graphic Novels',0.00,364,'http://books.google.com/books/content?id=RmqEBgAAQBAJ&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api&fife=w800','en','NOT_MATURE','BOOK');
/*!40000 ALTER TABLE `books` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `loans`
--

DROP TABLE IF EXISTS `loans`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `loans` (
  `loanId` int NOT NULL AUTO_INCREMENT,
  `loanDate` date NOT NULL,
  `dueDate` date NOT NULL,
  `returnDate` date DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `isbn13` varchar(20) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `userId` int NOT NULL,
  PRIMARY KEY (`loanId`),
  KEY `fk_loans_theses` (`isbn13`),
  KEY `fk_loans_users` (`userId`),
  CONSTRAINT `fk_loans_users` FOREIGN KEY (`userId`) REFERENCES `users` (`userId`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `loans`
--

LOCK TABLES `loans` WRITE;
/*!40000 ALTER TABLE `loans` DISABLE KEYS */;
INSERT INTO `loans` VALUES (4,'2004-04-04','2033-03-03','2005-01-01','returned','9781107632349','dog',12),(5,'2024-11-07','2023-11-15',NULL,'borrowed','9781471109416','How To Speak Dog',12);
/*!40000 ALTER TABLE `loans` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `preferences`
--

DROP TABLE IF EXISTS `preferences`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `preferences` (
  `userId` int NOT NULL,
  `categoryName` varchar(255) DEFAULT NULL,
  `categoryCount` int DEFAULT '1',
  KEY `userId` (`userId`),
  CONSTRAINT `preferences_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `users` (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `preferences`
--

LOCK TABLES `preferences` WRITE;
/*!40000 ALTER TABLE `preferences` DISABLE KEYS */;
INSERT INTO `preferences` VALUES (1,'Young Adult Fiction',1),(12,'Comics & Graphic Novels',1),(12,'Literary Criticism',1),(12,'Pets',1);
/*!40000 ALTER TABLE `preferences` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `theses`
--

DROP TABLE IF EXISTS `theses`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `theses` (
  `documentId` int NOT NULL AUTO_INCREMENT,
  `title` varchar(255) NOT NULL,
  `authors` text,
  `publishedDate` date DEFAULT NULL,
  `institution` varchar(255) DEFAULT NULL,
  `degree` varchar(50) DEFAULT NULL,
  `description` text,
  `categories` text,
  `language` varchar(10) DEFAULT NULL,
  `citationCount` int DEFAULT NULL,
  `availableCopies` int DEFAULT NULL,
  PRIMARY KEY (`documentId`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `theses`
--

LOCK TABLES `theses` WRITE;
/*!40000 ALTER TABLE `theses` DISABLE KEYS */;
INSERT INTO `theses` VALUES (1,'A Study on Quantum Computing','Alice Smith','2023-05-15','Harvard University','PhD','Explores the potential of quantum computing in various fields.','Computer Science, Quantum Physics','en',15,1),(2,'The Impact of Social Media on Youth','John Doe','2022-08-20','Stanford University','Master','Analyzes how social media influences the behavior of young people.','Sociology, Media Studies','en',25,2),(3,'Renewable Energy Sources and Sustainability','Emily Johnson','2021-11-10','MIT','Master','Discusses the importance of renewable energy for sustainable development.','Environmental Science, Energy','en',10,0),(4,'Artificial Intelligence in Healthcare','Bob Brown','2021-12-12','Health Institute','Masters','AI applications in healthcare.','Computer Science','English',20,4),(5,'Global Economic Trends','Emily Davis','2020-09-09','Business School','Bachelors','Analysis of economic trends.','Economics','English',5,3),(6,'Cybersecurity Challenges','Michael Green','2022-01-25','Cyber University','Masters','Investigating cybersecurity threats.','Information Technology','English',10,2),(7,'Education Reforms in the 21st Century','Sarah White','2023-06-30','Education University','PhD','Reforms in education systems.','Education','English',18,3),(8,'The Future of Work','David Black','2021-08-15','Business Institute','Masters','Workplace changes and future trends.','Business','English',14,2),(9,'Sustainable Agriculture Practices','Laura Blue','2022-04-18','Agricultural University','Bachelors','Sustainable farming methods.','Agriculture','English',7,1);
/*!40000 ALTER TABLE `theses` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `userId` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `phoneNumber` varchar(20) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `address` text,
  `membershipStatus` varchar(50) DEFAULT NULL,
  `privileges` varchar(255) DEFAULT NULL,
  `passwordHash` varchar(255) NOT NULL,
  PRIMARY KEY (`userId`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'Alice Johnson','123-456-7890','alice.johnson@example.com','123 Main St, Anytown, USA','Active','Standard',''),(2,'Bob Smith','098-765-4321','bob.smith@example.com','456 Elm St, Othertown, USA','Inactive','Standard',''),(3,'Charlie Brown','555-555-5555','charlie.brown@example.com','789 Pine St, Sometown, USA','Active','Admin','admin'),(4,'Daisy Ridley','555-4321','daisy.ridley@example.com','321 Maple St, Springfield','Active','Premium',''),(5,'Ethan Hunt','555-6789','ethan.hunt@example.com','654 Cedar St, Springfield','Active','Standard',''),(6,'Fiona Apple','555-1357','fiona.apple@example.com','987 Birch St, Springfield','Inactive','Premium',''),(7,'George Lucas','555-2468','george.lucas@example.com','159 Spruce St, Springfield','Active','Standard',''),(8,'Hannah Montana','555-3579','hannah.montana@example.com','753 Willow St, Springfield','Active','Premium',''),(9,'Ian McKellen','555-8642','ian.mckellen@example.com','321 Fir St, Springfield','Active','Standard',''),(10,'Jenna Ortega','555-9512','jenna.ortega@example.com','456 Aspen St, Springfield','Active','Premium',''),(11,'adf','920349320','sdf','waefawef','Active','Admin','1234'),(12,'abc','202340234','abc','abc','Active','Standard','$2a$10$H6O4xJylWEfGKycsFM3eOO7D0hYJ1Fy1x26XacofXBaaau8H8qaQW'),(18,'asf','asf','asf','asf','Active','Admin','$2a$10$6UUDxXiGkSYFln9.KNy7ROM1dbHVPjD2pjWMhLu4P2rQqAJ1RNxSG');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-11-07  9:40:40
