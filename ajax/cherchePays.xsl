<?xml version="1.0" encoding="UTF-8"?>

<!-- New XSLT document created with EditiX XML Editor (http://www.editix.com) at Tue Mar 26 20:51:05 CET 2019 -->

<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

	<xsl:output method="html"/>
	
	
<xsl:param name="pays"/>

	<xsl:template match="/">
	<html>
		<body>
		<p>
			<xsl:apply-templates select="//country[name/common=$pays]"/>
		</p>
		</body>
	</html>
	</xsl:template>
	
	<xsl:template match="country">
		<xsl:value-of select="$pays"/>
		<xsl:text>'s official name is: </xsl:text>
		<xsl:value-of select="./name/official"/>
		<xsl:text>. Its capital is:  </xsl:text>
		<xsl:value-of select="./capital"/>
	</xsl:template>

</xsl:stylesheet>


