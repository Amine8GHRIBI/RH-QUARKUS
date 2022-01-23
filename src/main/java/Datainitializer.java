

import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.Tuple;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@ApplicationScoped
public class Datainitializer {
    private final static Logger LOGGER = Logger.getLogger(Datainitializer.class.getName());

    @Inject
    PgPool client;

    public void onStart(@Observes StartupEvent ev) {
        LOGGER.info("The application is starting...");

        
        
        
        {
    		// Subject,Location,StartTime,EndTime,IsAllDay,StartTimezone,EndTimezone,ProjectId,TaskId,Description,RecurrenceRule,Id,RecurrenceException,RecurrenceID
    	/*	 client.query("DROP TABLE IF EXISTS Activities").execute() .flatMap(m ->
    		 client.query("CREATE TABLE Activities (" +
    		 "Subject character varying(50), "+
    		 "Location character varying(50), "+
    		 "StartTime character varying(50), " +
    		 "EndTime character varying(50), " +
    		 "IsAllDay boolean, "+
    		 "StartTimezone character varying(50), "+
    		 "EndTimezone character varying(50), "+
    		 "ProjectId character varying(50), "+
    		 "TaskId character varying(50), "+
    		 "Description character varying(1000), "+
    		 "RecurrenceRule character varying(50), "+
    		 "Id character varying(50), "+
    		 "RecurrenceException character varying(50), "+
    		 "RecurrenceID character varying(50), "+
    		 "PRIMARY KEY (Id))").execute()) 
    		 .flatMap(m -> client.query( "INSERT INTO Activities (Subject,Location,StartTime,EndTime,IsAllDay,StartTimezone,EndTimezone,ProjectId,TaskId,Description,RecurrenceRule,Id,RecurrenceException,RecurrenceID) VALUES('Gestion Panier','Talan Charguia','2021-04-26T08:30:00.000Z','2021-04-26T11:00:00.000Z',false,null,null,'1','1','Ajout d un produit dans le panier et checkout',null,'1',null,null)" ).execute())
    		 .flatMap(m -> client.query( "INSERT INTO Activities (Subject,Location,StartTime,EndTime,IsAllDay,StartTimezone,EndTimezone,ProjectId,TaskId,Description,RecurrenceRule,Id,RecurrenceException,RecurrenceID) VALUES('Meeting with Google Client','Talan Charguia','2021-04-26T11:30:00.000Z','2021-04-26T13:30:00.000Z',false,null,null,'1','1','Meeting with Google Manager Client',null,'2',null,null)" ).execute())
    		 .flatMap(m -> client.query( "INSERT INTO Activities (Subject,Location,StartTime,EndTime,IsAllDay,StartTimezone,EndTimezone,ProjectId,TaskId,Description,RecurrenceRule,Id,RecurrenceException,RecurrenceID) VALUES('Daily meeting','Talan Charguia','2021-04-26T08:30:00.000Z','2021-04-26T08:45:00.000Z',false,null,null,'1','2','Meeting pour synchroniser leur activites, discuter des problemes rencontes et de faire un plan pour les prochaines 24 heures ',null,'3',null,null)" ).execute())
    		 .flatMap(m -> client.query( "INSERT INTO Activities (Subject,Location,StartTime,EndTime,IsAllDay,StartTimezone,EndTimezone,ProjectId,TaskId,Description,RecurrenceRule,Id,RecurrenceException,RecurrenceID) VALUES('Data Analyse','Talan Charguia','2021-04-26T13:00:00.000Z','2021-04-26T17:00:00.000Z',false,null,null,'1','2','Determiner le pourcentage d avoir un cancer selon l alimentation et les pratiques quotidiennes',null,'4',null,null)" ).execute())
    		 .flatMap(m -> client.query( "INSERT INTO Activities (Subject,Location,StartTime,EndTime,IsAllDay,StartTimezone,EndTimezone,ProjectId,TaskId,Description,RecurrenceRule,Id,RecurrenceException,RecurrenceID) VALUES('Develop messaging framework','BMW Showroom Sousse','2021-04-26T14:00:00.000Z','2021-04-26T16:00:00.000Z',false,null,null,'2','3','',null,'5',null,null)" ).execute())
    		 .flatMap(m -> client.query( "INSERT INTO Activities (Subject,Location,StartTime,EndTime,IsAllDay,StartTimezone,EndTimezone,ProjectId,TaskId,Description,RecurrenceRule,Id,RecurrenceException,RecurrenceID) VALUES('Maintenance d un simulateur en ligne','Zitouna marsa','2021-04-26T08:00:00.000Z','2021-04-26T10:00:00.000Z',false,null,null,'2','3','',null,'6',null,null)" ).execute())
    		 .flatMap(m -> client.query( "INSERT INTO Activities (Subject,Location,StartTime,EndTime,IsAllDay,StartTimezone,EndTimezone,ProjectId,TaskId,Description,RecurrenceRule,Id,RecurrenceException,RecurrenceID) VALUES('Secure customer references','BIAT Nabeul','2021-04-26T16:00:00.000Z','2021-04-26T18:00:00.000Z',false,null,null,'2','4','',null,'7',null,null)" ).execute())
    		 .flatMap(m -> client.query( "INSERT INTO Activities (Subject,Location,StartTime,EndTime,IsAllDay,StartTimezone,EndTimezone,ProjectId,TaskId,Description,RecurrenceRule,Id,RecurrenceException,RecurrenceID) VALUES('Gestion d une plateforme de location de voiture','automobile rue les orangers Tunis','2021-04-26T09:30:00.000Z','2021-04-26T15:30:00.000Z',false,null,null,'2','4','',null,'8',null,null)" ).execute())
    		 .flatMap(m -> client.query( "INSERT INTO Activities (Subject,Location,StartTime,EndTime,IsAllDay,StartTimezone,EndTimezone,ProjectId,TaskId,Description,RecurrenceRule,Id,RecurrenceException,RecurrenceID) VALUES('Integration of recruitment module','Marhaba Hotel','2021-04-26T15:00:00.000Z','2021-04-26T17:00:00.000Z',false,null,null,'3','5','',null,'9',null,null)" ).execute())
    		 .flatMap(m -> client.query( "INSERT INTO Activities (Subject,Location,StartTime,EndTime,IsAllDay,StartTimezone,EndTimezone,ProjectId,TaskId,Description,RecurrenceRule,Id,RecurrenceException,RecurrenceID) VALUES('Testing d un logiciel de restauration','Talan Charguia','2021-04-26T10:00:00.000Z','2021-04-26T13:00:00.000Z',false,null,null,'3','5','',null,'10',null,null)" ).execute())
    		 .flatMap(m -> client.query( "INSERT INTO Activities (Subject,Location,StartTime,EndTime,IsAllDay,StartTimezone,EndTimezone,ProjectId,TaskId,Description,RecurrenceRule,Id,RecurrenceException,RecurrenceID) VALUES('Designing platefome Orange tunisie','Orange','2021-04-26T15:00:00.000Z','2021-04-26T17:00:00.000Z',true,null,null,'3','6','',null,'11',null,null)" ).execute())
    		 .flatMap(m -> client.query( "INSERT INTO Activities (Subject,Location,StartTime,EndTime,IsAllDay,StartTimezone,EndTimezone,ProjectId,TaskId,Description,RecurrenceRule,Id,RecurrenceException,RecurrenceID) VALUES('Integration of recruitment module','Marhaba Hotel','2021-04-26T09:00:00.000Z','2021-04-26T12:00:00.000Z',false,null,null,'3','2','As the only consumer-facing part of HR, the recruitment platform is the entry point to your organization for candidates and their information. Since most organizational data derives from the hiring process, it is crucial that it is clean and easily transferrable. Fortunately, when recruiting and HCM software are well-integrated, it allows for uninterrupted transfer of all data related to a person’s profile between platforms.',null,'12',null,null)" ).execute())
    		 .await()
    		 .indefinitely();
    		/*
        	client.query("DROP TABLE IF EXISTS projects").execute()
            .flatMap(m-> client.query("CREATE TABLE projects (id character varying(50) PRIMARY KEY NOT NULL, " +
                    "title character varying(200) NOT NULL, "+
                    "description character varying(500) NOT NULL, "+
                    "datedebut character varying(50) NOT NULL, "+
                    "datefin character varying(50) NOT NULL, "+
                    "client character varying(50) NOT NULL, "+
                    "FOREIGN KEY(client) REFERENCES clients(id))").execute())
            .flatMap(m -> client.query("INSERT INTO projects (id, title, description, datedebut, datefin,  client) VALUES('1','Projet de vente en ligne','un projet de developpement de site internet qui propose le positionnement, les catalogues de produits, la gestion des stocks, la vitrine, facture, paiement, communication','18/04/2021','29/5/2021','5')").execute())
            .flatMap(m -> client.query("INSERT INTO projects (id, title, description, datedebut, datefin,  client) VALUES('2','Projet ERP','Un logiciel pour l entreprise contenet les modules comptabilite, RH, gestion des contacts, marketing, service client','01/06/2021','11/07/2022','7')").execute())
            .flatMap(m -> client.query("INSERT INTO projects (id, title, description, datedebut, datefin,  client) VALUES('3','SEO-friendly website','un site qui est optimise autant par sa forme que ses contenus pour avoir un referencement naturel sur les moteurs de recherche','03/27/2021','26/04/2022','5')").execute())
            .flatMap(m -> client.query("INSERT INTO projects (id, title, description, datedebut, datefin,  client) VALUES('4','JavaScript quiz game','un Quiz qui couvrit les fonctionnalites de Classement, Minuteur du quiz, score, Randomiser les questions, ecran Conclusions à la fin du quiz.','08/02/2021','11/04/2022','6')").execute())
            .flatMap(m -> client.query("INSERT INTO projects (id, title, description, datedebut, datefin,  client) VALUES('5','To-do list','Application web permettant de collaborer evec une equipe en ajoutant ses tâches d une maniere fluide, facile et rapide','12/09/2021','11/01/2022','8')").execute())
            .flatMap(m -> client.query("INSERT INTO projects (id, title, description, datedebut, datefin,  client) VALUES('6','Developpement d une application Android de partage des bons plans','Le projet consiste au developpement d une application mobile qui propose des bon plans pour l utilisateur selon sa localisation geographique , elle contient aussi un reseau social pour partager les experiences de consommation et echanger les avis et les recommandations.','18/04/2021','29/5/2021','1')").execute())
            .flatMap(m -> client.query("INSERT INTO projects (id, title, description, datedebut, datefin,  client) VALUES('7','HQ trivia','L objectif de notre projet est de developper une plateforme de jeu temps reel et grand public.Il s agit d une variante de l application americaine HQ trivia.Le projet est dedie principalement aux operateurs de telecommunication afin de fideliser leurs clients d une part et cibler de nouveaux clients d autre part grace a l aspect publicitaire du jeu.','01/06/2021','11/07/2022','3')").execute())
            .flatMap(m -> client.query("INSERT INTO projects (id, title, description, datedebut, datefin,  client) VALUES('8','Design and implement DevOps concepts and tools','The goal being to improve the quality of devOps process in WRA. First, we studied devOps process used in WRA to define the limits, the gaps and the problems at the level of each entity of the segment which are codemove and operations. Then, from the notes collected, we defined ourselves the problems described below. The resolution of these problems would achieve our goal of improving the quality of devOps process in WRA.','03/27/2021','26/04/2022','6')").execute())
            .flatMap(m -> client.query("INSERT INTO projects (id, title, description, datedebut, datefin,  client) VALUES('9','Conception et realisation d un chatbot intelligent pour une plateforme de gestion de formation professionnelle','La mission de notre projet est la concéption et le developpement d un chatbot intelligent qui permet d assister les utilisateurs de la plateforme et de repondre a leurs questions durant leur navigation sur la plateforme.','08/02/2021','11/04/2022','7')").execute())
            .flatMap(m -> client.query("INSERT INTO projects (id, title, description, datedebut, datefin,  client) VALUES('10','application de messagerie instantanee en temps reel','Le projet consiste à realiser une application multiplateforme (IOS, Android, web). Cette application est nommee « Hola ». Hola permet l envoi de messages textes, des images, des videos, des audios et elle permet aussi de faire des appels vocaux, ainsi que des appels video.','12/09/2021','11/01/2022','2')").execute())
            .await()
            .indefinitely();
    		 */
        	/*
        	client.query("DROP TABLE IF EXISTS employees").execute()
            .flatMap(m-> client.query("CREATE TABLE employees (employee_id character varying(50) PRIMARY KEY NOT NULL, " +
                    "first_name character varying(50), "+
                    "last_name character varying(50), "+
                    "email character varying(50) )").execute())
            .flatMap(m -> client.query("INSERT INTO employees (employee_id,first_name,last_name,email) VALUES('1','Haythem','Bahri','Bahri@gmail.com')").execute())
            .flatMap(m -> client.query("INSERT INTO employees (employee_id,first_name,last_name,email) VALUES('2','Ahmed','Kazdar','Ahmed@gmail.com')").execute())
            .flatMap(m -> client.query ("INSERT INTO employees (employee_id,first_name,last_name,email) VALUES('3','Adam','Kossemtini','Adam@gmail.com')").execute())
            .flatMap(m -> client.query ("INSERT INTO employees (employee_id,first_name,last_name,email) VALUES('4','Anas','Abdelkefi','abdelkefianas@gmail.com')").execute())
            .flatMap(m -> client.query ("INSERT INTO employees (employee_id,first_name,last_name,email) VALUES('5','Malek','Slama','Malek@gmail.com')").execute())
            .await()
            .indefinitely();
        	
    		 client.query("DROP TABLE IF EXISTS clients").execute()
             .flatMap(m-> client.query("CREATE TABLE clients (id character varying(50) PRIMARY KEY NOT NULL, " +
                     "first_name character varying(50), "+
                     "last_name character varying(50), "+
                     "grade character varying(50), "+
                     "company character varying(50) )").execute())
             .flatMap(m -> client.query("INSERT INTO clients (id,first_name,last_name,grade,company) VALUES('1','John','Smith','CEO','vPro Infoweb LLC')").execute())
             .flatMap(m -> client.query("INSERT INTO clients (id,first_name,last_name,grade,company) VALUES('2','Gary','Camara','Marketing Head','Fly2 Infotech')").execute())
             .flatMap(m -> client.query("INSERT INTO clients (id,first_name,last_name,grade,company) VALUES('3','Hossein','Shams','CEO','BT Technology')").execute())
             .flatMap(m -> client.query("INSERT INTO clients (id,first_name,last_name,grade,company) VALUES('4','Maryam','Amiri','CEO','Core Infoweb Pvt')").execute())
             .flatMap(m -> client.query("INSERT INTO clients (id,first_name,last_name,grade,company) VALUES('5','Abdellaziz','Hammami','Manager','Talan')").execute())
             .flatMap(m -> client.query("INSERT INTO clients (id,first_name,last_name,grade,company) VALUES('6','Sundar','Pichai','PDG','Google')").execute())
             .flatMap(m -> client.query("INSERT INTO clients (id,first_name,last_name,grade,company) VALUES('7','Steve','Jobs','Fondateur','Apple')").execute())
             .flatMap(m -> client.query("INSERT INTO clients (id,first_name,last_name,grade,company) VALUES('8','Mark','Zuckerberg','Fondateur-PDG','Facebook')").execute())
             .await()
             .indefinitely();
    	 */
        	/*client.query("DROP TABLE IF EXISTS affectation").execute()
            .flatMap(m-> client.query("CREATE TABLE affectation ("+
                    "employee_id character varying(50) NOT NULL REFERENCES employees(employee_id), "+
                    "admin_id character varying(50) NOT NULL REFERENCES employees(employee_id), "+
                    "project_id character varying(50) NOT NULL REFERENCES projects(id), "+
                    "debut_affectation character varying(50) NOT NULL, "+
                    "fin_affectation character varying(50) NOT NULL, "+
                    "PRIMARY KEY (employee_id,admin_id,project_id))").execute())
            .flatMap(m -> client.query("INSERT INTO affectation (employee_id, admin_id, project_id,debut_affectation,fin_affectation) VALUES('1','b8649d85-5668-433d-8763-eca231ea2c5e','1','21/04/2021','21/06/2021')").execute())
            .flatMap(m -> client.query("INSERT INTO affectation (employee_id, admin_id, project_id,debut_affectation,fin_affectation) VALUES('2','b8649d85-5668-433d-8763-eca231ea2c5e','1','30/01/2021','01/01/2022')").execute())
            .flatMap(m -> client.query("INSERT INTO affectation (employee_id, admin_id, project_id,debut_affectation,fin_affectation) VALUES('3','b8649d85-5668-433d-8763-eca231ea2c5e','2','21/04/2021','21/06/2021')").execute())
            .flatMap(m -> client.query("INSERT INTO affectation (employee_id, admin_id, project_id,debut_affectation,fin_affectation) VALUES('b8649d85-5668-433d-8763-eca231ea2c5e','b8649d85-5668-433d-8763-eca231ea2c5e','2','21/04/2021','21/06/2021')").execute())
            .flatMap(m -> client.query("INSERT INTO affectation (employee_id, admin_id, project_id,debut_affectation,fin_affectation) VALUES('5','b8649d85-5668-433d-8763-eca231ea2c5e','3','21/04/2021','21/06/2021')").execute())
            .flatMap(m -> client.query("INSERT INTO affectation (employee_id, admin_id, project_id,debut_affectation,fin_affectation) VALUES('6','b8649d85-5668-433d-8763-eca231ea2c5e','3','30/01/2021','01/01/2022')").execute())
            .flatMap(m -> client.query("INSERT INTO affectation (employee_id, admin_id, project_id,debut_affectation,fin_affectation) VALUES('7','b8649d85-5668-433d-8763-eca231ea2c5e','4','21/04/2021','21/06/2021')").execute())
            .flatMap(m -> client.query("INSERT INTO affectation (employee_id, admin_id, project_id,debut_affectation,fin_affectation) VALUES('8','b8649d85-5668-433d-8763-eca231ea2c5e','4','21/04/2021','21/06/2021')").execute())
            .flatMap(m -> client.query("INSERT INTO affectation (employee_id, admin_id, project_id,debut_affectation,fin_affectation) VALUES('9','b8649d85-5668-433d-8763-eca231ea2c5e','5','21/04/2021','21/06/2021')").execute())
            .flatMap(m -> client.query("INSERT INTO affectation (employee_id, admin_id, project_id,debut_affectation,fin_affectation) VALUES('10','b8649d85-5668-433d-8763-eca231ea2c5e','5','30/01/2021','01/01/2022')").execute())
            .flatMap(m -> client.query("INSERT INTO affectation (employee_id, admin_id, project_id,debut_affectation,fin_affectation) VALUES('11','b8649d85-5668-433d-8763-eca231ea2c5e','1','21/04/2021','21/06/2021')").execute())
            .flatMap(m -> client.query("INSERT INTO affectation (employee_id, admin_id, project_id,debut_affectation,fin_affectation) VALUES('12','b8649d85-5668-433d-8763-eca231ea2c5e','2','21/04/2021','21/06/2021')").execute())
            .flatMap(m -> client.query("INSERT INTO affectation (employee_id, admin_id, project_id,debut_affectation,fin_affectation) VALUES('13','b8649d85-5668-433d-8763-eca231ea2c5e','1','21/04/2021','21/06/2021')").execute())
            .flatMap(m -> client.query("INSERT INTO affectation (employee_id, admin_id, project_id,debut_affectation,fin_affectation) VALUES('14','b8649d85-5668-433d-8763-eca231ea2c5e','2','30/01/2021','01/01/2022')").execute())
            .flatMap(m -> client.query("INSERT INTO affectation (employee_id, admin_id, project_id,debut_affectation,fin_affectation) VALUES('2','b8649d85-5668-433d-8763-eca231ea2c5e','3','21/04/2021','21/06/2021')").execute())
            .flatMap(m -> client.query("INSERT INTO affectation (employee_id, admin_id, project_id,debut_affectation,fin_affectation) VALUES('3','b8649d85-5668-433d-8763-eca231ea2c5e','4','21/04/2021','21/06/2021')").execute())
            .await()
            .indefinitely();*/
        	

    		/*
        	client.query("DROP TABLE IF EXISTS employees").execute()
            .flatMap(m-> client.query("CREATE TABLE employees (employee_id character varying(50) PRIMARY KEY NOT NULL, " +
                    "first_name character varying(50), "+
                    "last_name character varying(50), "+
                    "email character varying(50) )").execute())
            .flatMap(m -> client.query("INSERT INTO employees (employee_id,first_name,last_name,email) VALUES('1','Haythem','Bahri','Bahri@gmail.com')").execute())
            .flatMap(m -> client.query("INSERT INTO employees (employee_id,first_name,last_name,email) VALUES('2','Ahmed','Kazdar','Ahmed@gmail.com')").execute())
            .flatMap(m -> client.query ("INSERT INTO employees (employee_id,first_name,last_name,email) VALUES('3','Adam','Kossemtini','Adam@gmail.com')").execute())
            .flatMap(m -> client.query ("INSERT INTO employees (employee_id,first_name,last_name,email) VALUES('4','Anas','Abdelkefi','abdelkefianas@gmail.com')").execute())
            .flatMap(m -> client.query ("INSERT INTO employees (employee_id,first_name,last_name,email) VALUES('5','Malek','Slama','Malek@gmail.com')").execute())
            .flatMap(m -> client.query("INSERT INTO employees (employee_id,first_name,last_name,email) VALUES('6','Abdelkader','Garaali','Abdelkader@gmail.com')").execute())
            .flatMap(m -> client.query("INSERT INTO employees (employee_id,first_name,last_name,email) VALUES('7','Hadil','Laajili','Hadil@gmail.com')").execute())
            .flatMap(m -> client.query ("INSERT INTO employees (employee_id,first_name,last_name,email) VALUES('8','Ala Mehdi','Mersni','Koutaiba@gmail.com')").execute())
            .flatMap(m -> client.query ("INSERT INTO employees (employee_id,first_name,last_name,email) VALUES('9','Ghassen','Mechmech','Ghassen.Mechmech@gmail.com')").execute())
            .flatMap(m -> client.query ("INSERT INTO employees (employee_id,first_name,last_name,email) VALUES('10','Siwar','Monastiri','Siwar.Monastiri@gmail.com')").execute())
            .flatMap(m -> client.query ("INSERT INTO employees (employee_id,first_name,last_name,email) VALUES('11','Yosra','Bouasker','Yosra.Bouasker@gmail.com')").execute())
            .flatMap(m -> client.query ("INSERT INTO employees (employee_id,first_name,last_name,email) VALUES('12','Ala','Ben Abdallah','Ala.BenAbdallah@gmail.com')").execute())
            .flatMap(m -> client.query ("INSERT INTO employees (employee_id,first_name,last_name,email) VALUES('13','Ramsis','Fennira','Ramsis@gmail.com')").execute())
            .flatMap(m -> client.query ("INSERT INTO employees (employee_id,first_name,last_name,email) VALUES('14','Oussama','Fattouch','Oussama.Fattouch@gmail.com')").execute())
            .await()
            .indefinitely();
            */
    	}
        
        
        
        
        
        
        /*
        Tuple first = Tuple.of("Hello Quarkus", "My first post of Quarkus");
        Tuple second = Tuple.of("Hello Again, Quarkus", "My second post of Quarkus");

        client.query("DELETE FROM posts").execute()
                .flatMap(result -> client.preparedQuery("INSERT INTO posts (title, content) VALUES ($1, $2)").executeBatch(List.of(first, second)))
                .flatMap(rs -> client.query("SELECT * FROM posts").execute())
                .subscribe()
                .with(
                        rows -> rows.forEach(r -> LOGGER.log(Level.INFO, "data:{0}", r)),
                        err -> LOGGER.log(Level.SEVERE, "error:{0}", err)
                );
*/
    }

    private String readInitScript() {
        try {
            return Files.readString(Paths.get(this.getClass().getResource("/init.sql").toURI()), StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }

    void onStop(@Observes ShutdownEvent ev) {
        LOGGER.info("The application is stopping...");
    }
}



