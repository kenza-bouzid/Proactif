<?xml version="1.0" encoding="UTF-8"?>

<!-- New XSLT document created with EditiX XML Editor (http://www.editix.com) at Wed Mar 27 18:40:37 CET 2019 -->

<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="html"/>
	<xsl:param name="debut"/>
	<xsl:template match="/">
		<html>
			<body>
				<datalist>
					<xsl:choose>
						<xsl:when test="$debut=''">
							<xsl:for-each select="//country">
								<xsl:sort select="name/common"/>
								<xsl:variable name="pays">
									<xsl:value-of select="name/common"/>
								</xsl:variable>
								<option value="$pays"/>
							</xsl:for-each>
						</xsl:when>
					</xsl:choose>
					<xsl:for-each select="//country[starts-with(name/common,$debut)]">
						<xsl:sort select="name/common"/>
						<xsl:variable name="pays">
							<xsl:value-of select="name/common"/>
						</xsl:variable>
						<option value="$pays"/>
					</xsl:for-each>
				</datalist>
			</body>
		</html>
	</xsl:template>
</xsl:stylesheet>
