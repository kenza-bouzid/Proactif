<?xml version="1.0"?>

<xsl:stylesheet version  ="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

<xsl:template match="/">
  <HTML>
  <BODY bgcolor="#FFFFCC">
  <H1>Bibliographie</H1>
  <UL>
    <xsl:apply-templates select=".//BibRef[Book]"/>
  </UL>
  </BODY>
  </HTML>			
</xsl:template>

<xsl:template match="BibRef">
  <LI>
    <xsl:apply-templates select=".//Author"/>: 
    <xsl:value-of select=".//Title"/>.     
  </LI>
</xsl:template>

<xsl:template match="Author">
    <xsl:value-of select="."/>&amp; 
</xsl:template>

<xsl:template match="Author[position()=last()]">
    <xsl:value-of select="."/>
</xsl:template>

</xsl:stylesheet>
