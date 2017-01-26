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
                <!--NOTE: We are only expecting a transcript from one university-->
                <!--NOTE: The structure of the ApplicantProfile XML can handle more universities though-->
                <xsl:for-each select="document('Transcript.xml')/transcript/degreetranscript">
                    <xsl:element name="Degree">
                        <xsl:element name="School">
                            <xsl:value-of select="document('Transcript.xml')/transcript/schoolname" />
                        </xsl:element>
                        <xsl:element name="DegreeName">
                            <xsl:value-of select="degree" />
                        </xsl:element>
                        <xsl:element name="Graduated">
                            <xsl:value-of select="graduated" />
                        </xsl:element>
                        <xsl:element name="Transcript">
                            <xsl:attribute name="date">
                                <xsl:value-of select="document('Transcript.xml')/transcript/date" />
                            </xsl:attribute>
                            <xsl:for-each select="course">
                                <xsl:element name="Course">
                                    <xsl:element name="Name">
                                        <xsl:value-of select="name" />
                                    </xsl:element>
                                    <xsl:element name="Grade">
                                        <xsl:value-of select="grade" />
                                    </xsl:element>
                                </xsl:element>
                            </xsl:for-each>
                        </xsl:element>
                    </xsl:element>
                </xsl:for-each>
            </xsl:element>

            <xsl:element name="WorkExperience">
            </xsl:element>

        </xsl:element>
    </xsl:template>
</xsl:stylesheet>