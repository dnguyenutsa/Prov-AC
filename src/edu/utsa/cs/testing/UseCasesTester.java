package edu.utsa.cs.testing;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Collections;

import javax.xml.bind.JAXBException;

import org.openprovenance.jena.TripleStore;
import org.openprovenance.model.Account;
import org.openprovenance.model.Agent;
import org.openprovenance.model.Artifact;
import org.openprovenance.model.OPMFactory;
import org.openprovenance.model.OPMGraph;
import org.openprovenance.model.OPMSerialiser;
import org.openprovenance.model.OPMToDot;
import org.openprovenance.model.Overlaps;
import org.openprovenance.model.Process;
import org.openprovenance.model.Used;
import org.openprovenance.model.WasControlledBy;
import org.openprovenance.model.WasDerivedFrom;
import org.openprovenance.model.WasGeneratedBy;

import com.hp.hpl.jena.db.DBConnection;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.ResultSetFormatter;
import com.hp.hpl.jena.query.Syntax;
import com.hp.hpl.jena.rdf.model.Literal;  
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.NodeIterator;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.ResIterator;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import com.hp.hpl.jena.util.FileManager;
import com.hp.hpl.jena.vocabulary.DC;

public class UseCasesTester {

	/**
	 * @param args
	 */

	public static String OPMO_NS="http://openprovenance.org/model/opmo#";
	public static String OPMV_NS="http://purl.org/net/opmv/ns#";

	static Model model;
	static public OPMFactory oFactory=new OPMFactory();


	public static void main(String[] args) {
		String filename = "target/cake1.rdf";
		//readSampleRDFModel(filename);
		//runSimpleExample1();
		//runSimpleExample2();

		//runJDBCExample1();

		//		generatePSTSampleModel1();
		generatePSTSampleModel2();

		//executeSPARQLQuery1();
		//executeSPARQLQuery2(); // with GLEEN

		//runUseCaseOne();

	}

	/* Sample query */
	public static void executeSPARQLQuery1(){
		String prolog = "PREFIX opm:<" + OPMV_NS + ">";

		//		queryString = prolog + "\n" + "SELECT ?user WHERE { ?review1 " + model.getResource("wasControlledBy").getURI() + " ?user }";
		String queryString = prolog + "\n" + "SELECT DISTINCT ?user WHERE { ?review1 opm:wasControlledBy ?user }";

		Query query= QueryFactory.create( queryString, Syntax.syntaxARQ);
		QueryExecution qexec= QueryExecutionFactory.create( query, model );

		ResultSet rs= qexec.execSelect();
		ResultSetFormatter.out(System.out, rs, query);
	}

	public static void executeSPARQLQuery2(){ // with GLEEN
		/* Testing GLEEN */
		// specify query to execute
		String prolog = "PREFIX opmo:<" + OPMO_NS + ">";
		prolog += "PREFIX opmv:<" + OPMV_NS + ">";
		prolog += "\nPREFIX gleen:<java:edu.washington.sig.gleen.>";
		String queryString = prolog + "\n"
				+ "SELECT ?title WHERE { opmo:o4v1  gleen:OnPath( \"([opmv:wasGeneratedBy]/[opmv:used])/[opmv:wasGeneratedBy]/[opmv:wasControlledBy]\" ?title ) .}";

		// create and execute an instance of the specified query
		Query query= QueryFactory.create(queryString);
		QueryExecution qexec= QueryExecutionFactory.create( query, model );

		// store results in a set
		ResultSet rs= qexec.execSelect();

		// output query results to the screen
		ResultSetFormatter.out(System.out, rs, query); 
	}


	/* Sample data from PST 2012 Grading example */

	// Build with OPMToolbox
	public static void generatePSTSampleModel2(){
		Collection<Account> pst12=Collections.singleton(oFactory.newAccount("pst12"));

		Process upload1 =oFactory.newProcess("upload1",
				pst12,
				"upload1");
		Process submit1 =oFactory.newProcess("submit1",
				pst12,
				"submit1");
		Process replace1 =oFactory.newProcess("replace1",
				pst12,
				"replace1");
		Process review1 =oFactory.newProcess("review1",
				pst12,
				"review1");
		Process review2 =oFactory.newProcess("review2",
				pst12,
				"review2");
		Process revise1 =oFactory.newProcess("revise1",
				pst12,
				"revise1");
		Process grade1=oFactory.newProcess("grade1",
				pst12,
				"grade1");
		Process append1=oFactory.newProcess("append1",
				pst12,
				"append1");

		Agent au1=oFactory.newAgent("au1",
				pst12,
				"au1");
		Agent au2=oFactory.newAgent("au2",
				pst12,
				"au2");
		Agent au3=oFactory.newAgent("au3",
				pst12,
				"au3");
		Agent au4=oFactory.newAgent("au4",
				pst12,
				"au4");

		Artifact o1v1=oFactory.newArtifact("o1v1",
				pst12,
				"o1v1");
		Artifact o1v2=oFactory.newArtifact("o1v2",
				pst12,
				"o1v2");
		Artifact o1v3=oFactory.newArtifact("o1v3",
				pst12,
				"o1v3");
		Artifact o2v1=oFactory.newArtifact("o2v1",
				pst12,
				"o2v1");
		Artifact o2v2=oFactory.newArtifact("o2v2",
				pst12,
				"o2v2");
		Artifact o3v1=oFactory.newArtifact("o3v1",
				pst12,
				"o3v1");
		Artifact o4v1=oFactory.newArtifact("o4v1",
				pst12,
				"o4v1");
		Artifact o4v2=oFactory.newArtifact("o4v2",
				pst12,
				"o4v2");

		Used used1=oFactory.newUsed(replace1,oFactory.newRole("input"),o1v1,pst12);
		Used used2=oFactory.newUsed(submit1,oFactory.newRole("input"),o1v2,pst12);
		Used used3=oFactory.newUsed(review1,oFactory.newRole("input"),o1v3,pst12);
		Used used4=oFactory.newUsed(review2,oFactory.newRole("input"),o1v3,pst12);
		Used used5=oFactory.newUsed(revise1,oFactory.newRole("input"),o2v1,pst12);
		Used used6=oFactory.newUsed(grade1,oFactory.newRole("input"),o1v3,pst12);
		Used used7=oFactory.newUsed(append1,oFactory.newRole("ref"),o2v2,pst12);
		Used used8=oFactory.newUsed(append1,oFactory.newRole("src"),o4v1,pst12);

		WasGeneratedBy wg1=oFactory.newWasGeneratedBy(o1v1,oFactory.newRole("upload"),upload1,pst12);
		WasGeneratedBy wg2=oFactory.newWasGeneratedBy(o1v2,oFactory.newRole("replace"),replace1,pst12);
		WasGeneratedBy wg3=oFactory.newWasGeneratedBy(o1v3,oFactory.newRole("submit"),submit1,pst12);
		WasGeneratedBy wg4=oFactory.newWasGeneratedBy(o2v1,oFactory.newRole("review"),review1,pst12);
		WasGeneratedBy wg5=oFactory.newWasGeneratedBy(o3v1,oFactory.newRole("review"),review2,pst12);
		WasGeneratedBy wg6=oFactory.newWasGeneratedBy(o2v2,oFactory.newRole("revise"),revise1,pst12);
		WasGeneratedBy wg7=oFactory.newWasGeneratedBy(o4v1,oFactory.newRole("grade"),grade1,pst12);
		WasGeneratedBy wg8=oFactory.newWasGeneratedBy(o4v2,oFactory.newRole("append"),append1,pst12);

		WasControlledBy wc1=oFactory.newWasControlledBy(upload1,oFactory.newRole(""),au1,pst12);
		WasControlledBy wc2=oFactory.newWasControlledBy(replace1,oFactory.newRole(""),au1,pst12);
		WasControlledBy wc3=oFactory.newWasControlledBy(submit1,oFactory.newRole(""),au1,pst12);
		WasControlledBy wc4=oFactory.newWasControlledBy(review1,oFactory.newRole(""),au2,pst12);
		WasControlledBy wc5=oFactory.newWasControlledBy(revise1,oFactory.newRole(""),au2,pst12);
		WasControlledBy wc6=oFactory.newWasControlledBy(review2,oFactory.newRole(""),au3,pst12);
		WasControlledBy wc7=oFactory.newWasControlledBy(grade1,oFactory.newRole(""),au4,pst12);
		WasControlledBy wc8=oFactory.newWasControlledBy(append1,oFactory.newRole(""),au4,pst12);

		OPMGraph graph=oFactory.newOPMGraph(pst12,
				new Overlaps[] { },
				new Process[] {upload1,replace1,submit1,review1,review2,revise1,grade1,append1},
				new Artifact[] {o1v1,o1v2,o1v3,o2v1,o2v2,o3v1,o4v1,o4v2},
				new Agent[] { au1,au2,au3,au4 },
				new Object[] { used1,used2,used3,used4,used5,used6,used7,used8,
								wg1,wg2,wg3,wg4,wg5,wg6,wg7,wg8,
								wc1,wc2,wc3,wc4,wc5,wc6,wc7,wc8} );

		OPMSerialiser serial=OPMSerialiser.getThreadOPMSerialiser();
		try {
			serial.serialiseOPMGraph(new File("target/pst12.xml"),graph,true);
		} catch (JAXBException e) {
			e.printStackTrace();
		}

		OPMToDot toDot=new OPMToDot(true); // with roles

		try {
			toDot.convert(graph,"target/pst12.dot", "target/pst12.pdf");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void readSampleRDFModel(String inputFileName){
		// create an empty model
		Model model = ModelFactory.createDefaultModel();

		// use the FileManager to find the input file
		InputStream in = FileManager.get().open( inputFileName );
		if (in == null) {
			throw new IllegalArgumentException(
					"File: " + inputFileName + " not found");
		}

		// read the RDF/XML file
		model.read(in, null);

		// write it to standard out
		model.write(System.out);
	}

	// Build with Jena model
	public static void generatePSTSampleModel1(){

		String ns = OPMO_NS;
		model = ModelFactory.createDefaultModel();

		// Set prefix
		model.setNsPrefix("opmo", OPMO_NS);
		model.setNsPrefix("opmv", OPMV_NS);

		/* Generate artifacts */
		Resource o1v1 = model.createResource(ns+"o1v1");
		Resource o1v2 = model.createResource(ns+"o1v2");
		Resource o1v3 = model.createResource(ns+"o1v3");
		Resource o2v1 = model.createResource(ns+"o2v1");
		Resource o2v2 = model.createResource(ns+"o2v2");
		Resource o3v1 = model.createResource(ns+"o3v1");
		Resource o4v1 = model.createResource(ns+"o4v1");
		Resource o4v2 = model.createResource(ns+"o4v2");

		/* Generate processes */
		Resource uploadProcess1 = model.createResource(ns+"upload1");
		System.out.println("TESTING " + uploadProcess1.getNameSpace());
		//		Literal uploadProcess1_Role = model.createLiteral(ns+"toCreate");
		Resource submitProcess1 = model.createResource(ns+"submit1");
		Resource replaceProcess1 = model.createResource(ns+"replace1");
		Resource reviseProcess1 = model.createResource(ns+"revise1");
		Resource reviewProcess1 = model.createResource(ns+"review1");
		Resource reviewProcess2 = model.createResource(ns+"review2");
		Resource gradeProcess1 = model.createResource(ns+"grade1");
		Resource appendProcess1 = model.createResource(ns+"append1");

		/***DESIGN DECISION ***/
		/*How to implement base role dependency:
		 * 1) Generate and store instances of base dependencies and associate a role with each.
		 * 2) Associate different roles with each process instance and assign semantics with name.
		 */

		/* Generate agents */
		Resource actUsr1 = model.createResource(ns+"au1");
		Resource actUsr2 = model.createResource(ns+"au1");
		Resource actUsr3 = model.createResource(ns+"au1");
		Resource actUsr4 = model.createResource(ns+"au1");

		/* Generate base dependencies */
		ns = OPMV_NS;

		Property usedRinput = model.createProperty(ns+"usedRinput");
		Property usedRsource = model.createProperty(ns+"usedRsrc");
		Property usedRreference = model.createProperty(ns+"usedRref");

		Property wasGeneratedByRupload = model.createProperty(ns+"wasGeneratedByRupload");
		Property wasGeneratedByRreplace = model.createProperty(ns+"wasGeneratedByRreplace");
		Property wasGeneratedByRsubmit = model.createProperty(ns+"wasGeneratedByRsubmit");
		Property wasGeneratedByRreview = model.createProperty(ns+"wasGeneratedByRreview");
		Property wasGeneratedByRrevise = model.createProperty(ns+"wasGeneratedByRrevise");
		Property wasGeneratedByRgrade = model.createProperty(ns+"wasGeneratedByRgrade");
		Property wasGeneratedByRappend = model.createProperty(ns+"wasGeneratedByRappend");

		Property wasControlledBy = model.createProperty(ns+"wasControlledBy");
		Property wasTriggeredBy = model.createProperty(ns+"wasTriggeredBy");
		Property wasDerivedFrom = model.createProperty(ns+"wasDerivedFrom");



		/* Connect components into DAG */
		o1v1.addProperty(wasGeneratedByRupload, uploadProcess1);
		o1v2.addProperty(wasGeneratedByRreplace, replaceProcess1);
		o1v3.addProperty(wasGeneratedByRsubmit, submitProcess1);
		o2v1.addProperty(wasGeneratedByRreview, reviewProcess1);
		o2v2.addProperty(wasGeneratedByRrevise, reviseProcess1);
		o3v1.addProperty(wasGeneratedByRreview, reviewProcess2);
		o4v1.addProperty(wasGeneratedByRgrade, gradeProcess1);
		o4v2.addProperty(wasGeneratedByRappend, appendProcess1);

		uploadProcess1.addProperty(wasControlledBy, actUsr1);
		replaceProcess1.addProperty(usedRinput, o1v1).addProperty(wasControlledBy, actUsr1);
		submitProcess1.addProperty(usedRinput, o1v2).addProperty(wasControlledBy, actUsr1);
		reviewProcess1.addProperty(usedRinput, o1v3).addProperty(wasControlledBy, actUsr2);
		reviewProcess2.addProperty(usedRinput, o1v3).addProperty(wasControlledBy, actUsr3);
		reviseProcess1.addProperty(usedRinput, o2v1).addProperty(wasControlledBy, actUsr2);
		gradeProcess1.addProperty(usedRinput, o1v3).addProperty(wasControlledBy, actUsr4);
		appendProcess1.addProperty(usedRreference, o2v2).addProperty(usedRsource, o4v1).addProperty(wasControlledBy, actUsr4);

		/* Prints out model */
		model.write(System.out,"N-TRIPLE");
		model.write(System.out, "RDF/XML-ABBREV");

	}

	// Collaboration Use Case from CollaCom paper
	public static void runUseCaseOne(){

		String ns = "gcp:";
		Model m = ModelFactory.createDefaultModel() ;

		Resource org1o1v1 = m.createResource(ns+"org1o1v1");
		Resource org1o1v2 = m.createResource(ns+"org1o1v2");
		Resource org1o1v3 = m.createResource(ns+"org1o1v3");

		Resource createProcess = m.createResource(ns+"Create");
		Literal createRole = m.createLiteral(ns+"toCreate");

		Resource updateProcess = m.createResource(ns+"Update");
		Literal updateRole;

		Property used = m.createProperty(ns+"used");
		Property wasGeneratedBy = m.createProperty(ns+"wasGeneratedBy");
		Property wasControlledBy = m.createProperty(ns+"wasControlledBy");
		Property wasTriggeredBy = m.createProperty(ns+"wasTriggeredBy");
		Property wasDerivedFrom = m.createProperty(ns+"wasDerivedFrom");

		Property wasCopyOf = m.createProperty(ns+"wasCopyOf");
		Property wasNewVersionOf = m.createProperty(ns+"wasNewVersionOf");
		Property HadAdmin = m.createProperty(ns+"HadAdmin");

		org1o1v1.addProperty(wasGeneratedBy, createProcess);
		createProcess.addLiteral(m.createProperty(ns+"role"), createRole);

		org1o1v2.addProperty(wasGeneratedBy, updateProcess)
		.addProperty(wasNewVersionOf, org1o1v1);

		org1o1v3.addProperty(wasGeneratedBy, updateProcess)
		.addProperty(wasNewVersionOf, org1o1v1);

		updateProcess.addProperty(m.createProperty(ns+"used"), org1o1v1);

		//		m.write(System.out,"N-TRIPLE");
		//org1o1v1.add

	}

	public static void runSimpleExample1(){
		Model model1 = ModelFactory.createDefaultModel();

		Resource r1 = model1.createResource("http://example.org/book#1");
		Resource r2 = model1.createResource( "http://example.org/book#2" ) ;
		Resource r3 = model1.createResource( "http://example.org/book#3" ) ;

		//	r4.addProperty(DC.description, r2);
		//	m2.write(System.out, "RDF/XML-ABBREV");

		String nsResource = "http://example.org/book#";
		model1.setNsPrefix("nsResource", nsResource);

		System.out.println("TEsting\n " + DC.title.getNameSpace());

		r1.addProperty(DC.title, "SPARQL-the book" ).addProperty(DC.description, "A book about SPARQL" ).addProperty(DC.description, r2) ;
		//	r2.addProperty(DC.title, "Advanced techniques for SPARQL" ).addProperty(DC.description, r3).addProperty(DC.description, r1) ;
		//	r3.addProperty(DC.title, "Jena -an RDF framework for Java" ).addProperty(DC.description, "A book about Jena").addProperty(DC.description, r1) ;
		r2.addProperty(DC.title, "Advanced techniques for SPARQL" ).addProperty(DC.description, r1);

		System.out.println(DC.description.getURI());
		System.out.println(DC.getURI());

		model1.write(System.out,"N-TRIPLE");
		model1.write(System.out, "RDF/XML-ABBREV");


		String prolog = "PREFIX dc:<" + DC.getURI() + ">";
		//		prolog = "";
		String queryString= prolog + "\n" 
				+ "SELECT ?title WHERE { ?x dc:description ?y . ?y dc:description?z . ?z dc:title?title }";

		Query query= QueryFactory.create( queryString);
		QueryExecution qexec= QueryExecutionFactory.create( query, model1 );

		ResultSet rs= qexec.execSelect();
		ResultSetFormatter.out(System.out, rs, query);


		System.out.println("\nGLEEN\n");

		/* Testing GLEEN */
		// specify query to execute
		prolog += "\nPREFIX gleen:<java:edu.washington.sig.gleen.>";
		queryString = prolog + "\n"
				+ "SELECT ?title WHERE {  <http://example.org/book#1> gleen:OnPath(\"([dc:description]/[dc:description])*/[dc:title]\" ?title ) .}";

		// create and execute an instance of the specified query
		query= QueryFactory.create(queryString);
		qexec= QueryExecutionFactory.create( query, model1 );

		// store results in a set
		rs= qexec.execSelect();

		// output query results to the screen
		ResultSetFormatter.out(System.out, rs, query); 

		// attempt to access the results one by one
		// so far unable
		try {
			//				ResultSet res= qexec.execSelect();
			//System.out.println(rs.getResultVars().toString());
			//				for ( ; rs.hasNext() ; ) {
			//					QuerySolution rb= rs.nextSolution() ;
			//System.out.println(rb);
			//					RDFNode x = rb.get( "title" ) ;
			//					System.out.println(x);
			//					if ( x.isLiteral() ) {
			//						Literal titleStr= (Literal )x ;
			//						System.out.println( " " + titleStr); 
			//
			//					}
			//				}
		}
		finally { qexec.close(); }

	}

	public static void runSimpleExample2(){
		// URI declarations
		String familyUri = "http://family/";
		String relationshipUri = "http://purl.org/vocab/relationship/";

		// Create an empty Model
		Model model = ModelFactory.createDefaultModel();

		// Create a Resource for each family member, identified by their URI
		Resource adam = model.createResource(familyUri+"adam");
		Resource beth = model.createResource(familyUri+"beth");
		Resource chuck = model.createResource(familyUri+"chuck");
		Resource dotty = model.createResource(familyUri+"dotty");
		Resource edward = model.createResource(familyUri+"edward");

		// Create properties for the different types of relationship to represent
		Property childOf = model.createProperty(relationshipUri, "childOf");
		Property parentOf = model.createProperty(relationshipUri, "parentOf");
		Property siblingOf = model.createProperty(relationshipUri, "siblingOf");
		Property spouseOf = model.createProperty(relationshipUri, "spouseOf");

		// Add properties to adam describing relationships to other family members
		adam.addProperty(siblingOf, beth);
		adam.addProperty(spouseOf, dotty);
		adam.addProperty(parentOf, chuck);

		// Create statements directly
		Statement stmt = model.createStatement(adam, parentOf, edward);
		model.add(stmt);

		// Print model
		model.write(System.out,"N-TRIPLE");
		//		model.write(System.out, "RDF/XML-ABBREV");

		String prolog = "PREFIX rel:<" + relationshipUri + ">";
		//		prolog = "";
		String queryString= prolog + "\n" 
				+ "SELECT ?parent WHERE { ?parent rel:parentOf ?child }";

		Query query= QueryFactory.create( queryString);
		QueryExecution qexec= QueryExecutionFactory.create( query, model );

		ResultSet rs= qexec.execSelect();
		ResultSetFormatter.out(System.out, rs, query);

		// List everyone in the model who has a child
		// Get a ResIterator
		ResIterator parents = model.listSubjectsWithProperty(parentOf);

		while (parents.hasNext()){

			// ResIterator has a typed nextResource() method
			Resource person = parents.nextResource();

			// Print the URI of the resource
			System.out.println(person.getURI());
		}

		// Different ways to use NodeIterator
		NodeIterator moreParents = model.listObjectsOfProperty(childOf);
		NodeIterator siblings = model.listObjectsOfProperty(edward, siblingOf);

		// Use of StmtIterator
		StmtIterator moreSiblings = edward.listProperties(siblingOf);

		// Find the exact statement "adam is a spouse of dotty"
		model.listStatements(adam, spouseOf, dotty);

		//Find all statements with adam as the sbj and dotty as the obj
		model.listStatements(adam, null, dotty);

	}

	static void runJDBCExample1() throws ClassNotFoundException {

		// Instantiate the MySQL driver
		Class.forName("com.mysql.jdbc.Driver");

		String DB_URL = "jdbc:mysql://localhost/dbname";

		// Create a database connection object
		//		DBConnection connection = new DBConnection(DB_URL, DB_USER, DB_PASSWORD, DB_TYPE);

	}

	static void runOPMToolExample1(){
		TripleStore ts = new TripleStore();
	}
}
