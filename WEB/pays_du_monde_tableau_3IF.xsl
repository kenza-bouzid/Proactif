<?xml version="1.0" encoding="UTF-8"?>

<!-- New XSLT document created with EditiX XML Editor (http://www.editix.com) at Wed Mar 20 16:12:02 CET 2019 -->

<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="html"/>
	<xsl:template match="/">
		<html>
			<head>
				<title>
    			Pays du monde 
  			</title>
			</head>
			<body style="background-color:white;">
				<h1>Les pays du monde</h1>
      		Mise en forme par : BOUZID Kenza, HAMIDOVIC David (B3140)
      		<xsl:apply-templates select="//metadonnees"/>
				<hr width="100%" color="black"/><!-- Affichez le nom commun du pays ayant le plus court nom commun  -->
				<xsl:text>Pays avec 6 voisins : </xsl:text>
				<xsl:for-each select="//name/common[count(../..//neighbour)=6]">
					<xsl:value-of select="current()"/>
					<xsl:if test="not(position()=last())">
						<xsl:text>, </xsl:text>
					</xsl:if>
				</xsl:for-each>
				<br/>
				<br/><!-- Affichez les noms communs des pays ayant 6 voisins -->
				<xsl:for-each select="//name/common[not(preceding::common = .)]">
					<xsl:sort select="string-length(current())" data-type="number" order="ascending"/>
					<xsl:if test="position() = 1">
						<xsl:text>Pays ayant le plus court nom : </xsl:text>
						<xsl:value-of select="current()"/>
					</xsl:if>
				</xsl:for-each>
				<hr width="100%" color="black"/><!-- Séparez les pays d'abord par contients, puis par sous-continents --><!-- Pour chaque souscontinent, indiquez son nom et le nombre de pays avant le tableau -->
				<xsl:for-each select="//continent[not(preceding::continent = .)]">
					<xsl:variable name="curCountry" select="current()"/>
					<xsl:for-each select="//subregion[not(preceding::subregion = .) and ../continent = $curCountry]">
						<h3>
						Pays du continent: <xsl:value-of select="$curCountry"/>
							<xsl:text> par sous-régions:</xsl:text>
						</h3>
						<h4>
							<xsl:value-of select="current()"/>
							<xsl:text> (</xsl:text>
							<xsl:value-of select="count(//country[infosContinent/subregion=current()])"/>
							<xsl:text>)</xsl:text>
						</h4>
						<table border="3" width="100%" align="center">
							<tr>
								<th>N°</th>
								<th>Nom</th>
								<th>Capitale</th>
								<th>Voisins</th>
								<th>Coordonnées</th>
								<th>Drapeau</th>
							</tr>
							<xsl:apply-templates select="//country[infosContinent/subregion= current()]"/>
						</table>
					</xsl:for-each>
				</xsl:for-each>
			</body>
		</html>
	</xsl:template>
	<xsl:template match="metadonnees">
		<p style="text-align:center; color:blue;">
		Objectif : <xsl:value-of select="objectif"/>
		</p>
		<hr/>
	</xsl:template>
	<xsl:template match="country">
		<tr><!--  le numéro de pays lors de l'affichage -->
			<td>
				<xsl:value-of select="position()"/>
			</td><!--le nom commun du pays en vert dans la première cellule-->
			<td>
				<span style="color:green">
					<xsl:value-of select="name/common"/>
				</span><!-- entre parenthèses le nom officiel du pays -->
				<xsl:text> (</xsl:text>
				<xsl:value-of select="name/official"/>
				<xsl:text>)</xsl:text>
				<xsl:if test="name/native_name/@lang ='fra'">
					<br/><!-- Si le pays a un nom en Français, affichez le en marron -->
					<span style="color:brown">
						Nom Français: <xsl:value-of select="name/native_name[@lang='fra']/official"/>
					</span>
				</xsl:if>
			</td><!-- le nom de la capitale -->
			<td>
				<xsl:value-of select="capital"/>
			</td><!--  les noms communs des voisins -->
			<td>
				<xsl:for-each select="borders/neighbour">
					<xsl:value-of select="//country[codes/cca3=current()]/name/common"/>
					<xsl:if test="not(position()=last())">
						<xsl:text>, </xsl:text>
					</xsl:if>
				</xsl:for-each>
				<xsl:if test="count(borders/neighbour)=0 and ./landlocked='false'">
					<xsl:text>Île</xsl:text>
				</xsl:if>
			</td><!-- la latitude et la longitude des pays  -->
			<td>
				Latitude : <xsl:value-of select="coordinates/@lat"/>
				<br/>
				Longitude : <xsl:value-of select="coordinates/@long"/>
			</td><!-- le drapeau du pays -->
			<td>
				<xsl:variable name="url" select="concat('http://www.geonames.org/flags/x/',translate(codes/cca2 , 'ABCDEFGHIJKLMNOPQRSTUVWXYZ' , 'abcdefghijklmnopqrstuvwxyz' ), '.gif')"/>
				<img src="{$url}" alt="" height="40" width="60"/>
			</td>
		</tr>
	</xsl:template>
</xsl:stylesheet>
