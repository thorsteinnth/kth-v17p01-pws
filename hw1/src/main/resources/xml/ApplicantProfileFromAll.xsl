<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:template match="/">
        <xsl:element name="ApplicantProfile">
            <xsl:element name="General">
                <xsl:element name="SSN">
                    <xsl:value-of select="document('Resume.xml')/resume/basicinfo/ssn" />
                </xsl:element>
                <xsl:element name="Name">
                    <xsl:value-of select="document('Resume.xml')/resume/basicinfo/name" />
                </xsl:element>
                <xsl:element name="Address">
                    <xsl:value-of select="document('Resume.xml')/resume/basicinfo/address" />
                </xsl:element>
                <xsl:element name="City">
                    <xsl:value-of select="document('Resume.xml')/resume/basicinfo/city" />
                </xsl:element>
                <xsl:element name="Country">
                    <xsl:value-of select="document('Resume.xml')/resume/basicinfo/country" />
                </xsl:element>
                <xsl:element name="ContactInfo">
                    <xsl:element name="Telephone">
                        <xsl:value-of select="document('Resume.xml')/resume/contactinfo/telephonenumber" />
                    </xsl:element>
                    <xsl:element name="Email">
                        <xsl:value-of select="document('Resume.xml')/resume/contactinfo/email" />
                    </xsl:element>
                </xsl:element>
                <xsl:element name="Preferences">
                    <xsl:element name="JobType">
                        <xsl:value-of select="document('Resume.xml')/resume/otherinfo/typeofjob" />
                    </xsl:element>
                    <xsl:element name="MotivationLetter">
                        <xsl:value-of select="document('Resume.xml')/resume/otherinfo/motivationletter" />
                    </xsl:element>
                    <xsl:element name="Comments">
                        <xsl:value-of select="document('Resume.xml')/resume/otherinfo/othercomments" />
                    </xsl:element>
                    <xsl:element name="DesiredWorkplace">
                        <xsl:value-of select="document('Resume.xml')/resume/placesdesiredtowork" />
                    </xsl:element>
                </xsl:element>
            </xsl:element>
            <xsl:element name="Education">
            </xsl:element>
            <xsl:element name="WorkExperience">
            </xsl:element>
        </xsl:element>
    </xsl:template>
</xsl:stylesheet>