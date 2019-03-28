<?xml version="1.0" encoding="UTF-8"?>

<!-- New XSLT document created with EditiX XML Editor (http://www.editix.com) at Tue Mar 26 20:51:05 CET 2019 -->

	<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="html"/>
	<xsl:param name="pays"/>
	<xsl:param name="id"/>
	<xsl:template match="/">
		<html>
			<body>
				<table border="3" width="100%" align="center">
					<tr>
						<th>Name</th>
						<th>Capital</th>
						<th>Flag</th>
						<th>More information</th>
					</tr>
					<xsl:apply-templates select="//country[name/common=$pays]"/>
				</table>
			</body>
		</html>
	</xsl:template>
	
	<xsl:template match="country">
		<tr>
			<td>
				<span style="color:green">
					<xsl:value-of select="name/common"/>
				</span>
				<xsl:text> (</xsl:text>
				<xsl:value-of select="name/official"/>
				<xsl:text>)</xsl:text>
				<xsl:if test="name/native_name/@lang ='fra'">
					<br/>
					<span style="color:brown">
						Nom Français: <xsl:value-of select="name/native_name[@lang='fra']/official"/>
					</span>
				</xsl:if>
			</td>
			<td>
				<xsl:value-of select="capital"/>
			</td>
			<td>
				<xsl:variable name="url" select="concat('http://www.geonames.org/flags/x/',translate(codes/cca2 , 'ABCDEFGHIJKLMNOPQRSTUVWXYZ' , 'abcdefghijklmnopqrstuvwxyz' ), '.gif')"/>
				<img src="{$url}" alt="" height="40" width="60"/>
			</td>
			<td>
				<a href="https://www.geonames.org/search.html?q=&amp;country={$id}" target="_blank">Learn more on geonames</a> 
			</td>
		</tr>
	</xsl:template>
</xsl:stylesheet>
