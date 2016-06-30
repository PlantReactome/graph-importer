package org.reactome.server.graph;

import com.martiansoftware.jsap.*;
import org.reactome.server.graph.batchimport.ReactomeBatchImporter;

import java.io.IOException;

/**
 * Created by:
 *
 * @author Florian Korninger (florian.korninger@ebi.ac.uk)
 * @since 13.11.15.
 */
public class DataImportLauncher {

    public static void main(String[] args) throws JSAPException, IOException {


        SimpleJSAP jsap = new SimpleJSAP(DataImportLauncher.class.getName(), "A tool for importing reactome data import to the neo4j graphDb",
                new Parameter[]{
                        new FlaggedOption("host",     JSAP.STRING_PARSER, "localhost",  JSAP.NOT_REQUIRED, 'h', "host",     "The database host"),
                        new FlaggedOption("port",     JSAP.INTEGER_PARSER,"3306",       JSAP.NOT_REQUIRED, 's', "port",     "The reactome port"),
                        new FlaggedOption("name",     JSAP.STRING_PARSER, "reactome",   JSAP.NOT_REQUIRED, 'd', "name",     "The reactome database name to connect to"),
                        new FlaggedOption("user",     JSAP.STRING_PARSER, "reactome",   JSAP.NOT_REQUIRED, 'u', "user",     "The database user"),
                        new FlaggedOption("password", JSAP.STRING_PARSER, "reactome",   JSAP.NOT_REQUIRED, 'p', "password", "The password to connect to the database"),
                        new FlaggedOption("neo4j",    JSAP.STRING_PARSER, "./target/graph.db",   JSAP.NOT_REQUIRED, 'n', "neo4j", "Path to the neo4j database ")
                }
        );

        JSAPResult config = jsap.parse(args);
        if (jsap.messagePrinted()) System.exit(1);

        /**
         * @Autowired annotation does not work in a static context. context.getBean has to be used instead.
         * final AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(MyConfiguration.class);
         * ReactomeBatchImporter batchImporter = ctx.getBean(ReactomeBatchImporter.class);
         */
        ReactomeBatchImporter batchImporter = new ReactomeBatchImporter(
                config.getString("host"),
                config.getInt("port"),
                config.getString("name"),
                config.getString("user"),
                config.getString("password"),
                config.getString("neo4j"));
        batchImporter.importAll();
    }
}