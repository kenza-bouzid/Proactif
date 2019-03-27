//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

function recupererPremierEnfantDeTypeNode(n) {
    var x = n.firstChild;
    while (x.nodeType != 1) { // Test if x is an element node (and not a text node or other)
        x = x.nextSibling;
    }
    return x;
}
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//change le contenu de l'élement avec l'id "nom" avec la chaine de caractéres en paramètre	  
function setNom(nom) {
    var elementHtmlARemplir = window.document.getElementById("id_nom_a_remplacer");
    elementHtmlARemplir.innerHTML = nom;
}

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//change la couleur du background en bleu et la couleur du texte du boutton en blanc 
function Bouton1_setBG() {
    window.document.body.style.backgroundColor = 'blue';
	window.document.getElementById('myButton1').style.color = 'white' ;
}

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//change la couleur du background en bleu et la couleur du texte du boutton en blanc 
function Bouton2_resetBG() {
	window.document.body.style.backgroundColor = 'white';
	window.document.getElementById('myButton1').style.color = 'black' ;
}


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//charge le fichier XML se trouvant à l'URL relative donné dans le paramètreet le retourne
function chargerHttpXML(xmlDocumentUrl) {

    var httpAjax;

    httpAjax = window.XMLHttpRequest ?
        new XMLHttpRequest() :
        new ActiveXObject('Microsoft.XMLHTTP');

    if (httpAjax.overrideMimeType) {
        httpAjax.overrideMimeType('text/xml');
    }

    //chargement du fichier XML à l'aide de XMLHttpRequest synchrone (le 3° paramètre est défini à false)
    httpAjax.open('GET', xmlDocumentUrl, false);
    httpAjax.send();

    return httpAjax.responseXML;
}

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////  
// Charge le fichier JSON se trouvant à l'URL donnée en paramètre et le retourne
function chargerHttpJSON(jsonDocumentUrl) {

    var httpAjax;

    httpAjax = window.XMLHttpRequest ?
        new XMLHttpRequest() :
        new ActiveXObject('Microsoft.XMLHTTP');

    if (httpAjax.overrideMimeType) {
        httpAjax.overrideMimeType('text/xml');
    }

    // chargement du fichier JSON à l'aide de XMLHttpRequest synchrone (le 3° paramètre est défini à false)
    httpAjax.open('GET', jsonDocumentUrl, false);
    httpAjax.send();

    var responseData = eval("(" + httpAjax.responseText + ")");

    return responseData;
}



//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
function Bouton3_nomOffCap(xmlDocumentUrl, xslDocumentUrl) {

    var xsltProcessor = new XSLTProcessor();

    // Chargement du fichier XSL à l'aide de XMLHttpRequest synchrone 
    var xslDocument = chargerHttpXML(xslDocumentUrl);

    // Importation du .xsl
	var param = document.getElementById('myText1').value ; 
	console.log(param); 
	xsltProcessor.setParameter(null,"pays",param); 
	xsltProcessor.importStylesheet(xslDocument);
	
    // Chargement du fichier XML à l'aide de XMLHttpRequest synchrone 
    var xmlDocument = chargerHttpXML(xmlDocumentUrl);
    // Création du document XML transformé par le XSL
    var newXmlDocument = xsltProcessor.transformToDocument(xmlDocument);

    // Recherche du parent (dont l'id est "here") de l'élément à remplacer dans le document HTML courant
    var elementHtmlParent = document.getElementById("cap");
    var elementAInserer = newXmlDocument.getElementsByTagName('p');
	elementHtmlParent.innerText = elementAInserer[0].innerText; 
	document.getElementById("myText1").value = ''
}

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
function Bouton4_svg(xmlDocumentUrl , param) {
	
    var xmlDocument = chargerHttpXML(xmlDocumentUrl);
	var elementHtml = document.getElementById(param);
	var elementAInserer= xmlDocument.querySelector('svg'); 
	var oSerializer = new XMLSerializer();
	elementHtml.innerHTML = oSerializer.serializeToString(elementAInserer);

}

function clickable (param1 , param2 , param3){

	var exemple = document.querySelector(param1); 
	
	for (var i = 0 ; i < exemple.children.length ; i++) {
		var child = exemple.children[i]; 
		child.addEventListener('click', function(evt){	
			var elementARemplacer = document.getElementById(param2);
			elementARemplacer.innerText = evt.target.getAttribute(param3);
			});
	}
	}


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
function Bouton8_stylish() {
	var carte = document.querySelector('#world svg g'); 
	for (var i = 0 ; i < carte.children.length ; i++) {
		var country = carte.children[i]; 
		country.addEventListener('mouseover' , colorier); 
		country.addEventListener('mouseleave' , function(event){
			event.target.style.fill = ""; 	
			
		});
	}
}

function colorier(event){
	event.target.style.fill = "lightblue"; 
	var xsltProcessor = new XSLTProcessor();
	// Chargement du fichier XSL à l'aide de XMLHttpRequest synchrone 
	var xslDocument = chargerHttpXML('cherchePays2.xsl');
	var param = event.target.getAttribute('countryname');
	console.log(param);
	xsltProcessor.setParameter(null,"pays",param); 
	xsltProcessor.importStylesheet(xslDocument);
	 // Chargement du fichier XML à l'aide de XMLHttpRequest synchrone 
    var xmlDocument = chargerHttpXML('countriesTP.xml');
    // Création du document XML transformé par le XSL
    var newXmlDocument = xsltProcessor.transformToDocument(xmlDocument);
	var eltHtml = document.getElementById('tab'); 
	var eltXml = newXmlDocument.getElementsByTagName('table')[0]; 
	var oSerializer = new XMLSerializer();
	eltHtml.innerHTML = oSerializer.serializeToString(eltXml);

}


function autoComp(){
	var xmlDoc = chargerHttpXML('countriesTP.xml');
	var nsResolver = xmlDoc.createNSResolver( xmlDoc.ownerDocument == null ? xmlDoc.documentElement : xmlDoc.ownerDocument.documentElement);
	var param = document.getElementById('myText1').value; 
	var countryIterator = xmlDoc.evaluate("//country[starts-with(name/common,translate(param , 'ABCDEFGHIJKLMNOPQRSTUVWXYZ' , 'abcdefghijklmnopqrstuvwxyz' ))]/name/common", xmlDoc, nsResolver, XPathResult.ORDERED_NODE_ITERATOR_TYPE, null );
	var options = '';
	try {
	var thisNode = countryIterator.iterateNext();
		while (thisNode) {
			options += '<option value="'+thisNode.textContent+'" />';
			thisNode = countryIterator.iterateNext();
		}	
	}
	catch (e) {
	console.log( 'Erreur : '+ e );
	}
	document.getElementById('countries').innerHTML = options; 

}
	
	
	
	


