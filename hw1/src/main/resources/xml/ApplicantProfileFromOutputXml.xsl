<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:ap="http://www.bestrecruitment.com" version="1.0">
    <xsl:template match="/">
        <xsl:element name="ap:ApplicantProfile">
            <xsl:element name="ap:General">
                <xsl:element name="ap:SSN">
                    <xsl:value-of select="document('../../../../output_xml/OutputResume.xml')/resume/basicinfo/ssn" />
                </xsl:element>
                <xsl:element name="ap:Name">
                    <xsl:value-of select="document('../../../../output_xml/OutputResume.xml')/resume/basicinfo/name" />
                </xsl:element>
                <xsl:element name="ap:Address">
                    <xsl:value-of select="document('../../../../output_xml/OutputResume.xml')/resume/basicinfo/address" />
                </xsl:element>
                <xsl:element name="ap:City">
                    <xsl:value-of select="document('../../../../output_xml/OutputResume.xml')/resume/basicinfo/city" />
                </xsl:element>
                <xsl:element name="ap:Country">
                    <xsl:value-of select="document('../../../../output_xml/OutputResume.xml')/resume/basicinfo/country" />
                </xsl:element>
                <xsl:element name="ap:ContactInfo">
                    <xsl:element name="ap:Telephone">
                        <xsl:value-of select="document('../../../../output_xml/OutputResume.xml')/resume/contactinfo/telephonenumber" />
                    </xsl:element>
                    <xsl:element name="ap:Email">
                        <xsl:value-of select="document('../../../../output_xml/OutputResume.xml')/resume/contactinfo/email" />
                    </xsl:element>
                </xsl:element>
                <xsl:element name="ap:Preferences">
                    <xsl:element name="ap:JobType">
                        <xsl:value-of select="document('../../../../output_xml/OutputResume.xml')/resume/otherinfo/typeofjob" />
                    </xsl:element>
                    <xsl:element name="ap:MotivationLetter">
                        <xsl:value-of select="document('../../../../output_xml/OutputResume.xml')/resume/otherinfo/motivationletter" />
                    </xsl:element>
                    <xsl:element name="ap:Comments">
                        <xsl:value-of select="document('../../../../output_xml/OutputResume.xml')/resume/otherinfo/othercomments" />
                    </xsl:element>
                    <xsl:element name="ap:DesiredWorkplace">
                        <xsl:value-of select="document('../../../../output_xml/OutputResume.xml')/resume/placesdesiredtowork" />
                    </xsl:element>
                </xsl:element>
            </xsl:element>
            <xsl:element name="ap:Education">
                <!--NOTE: We are only expecting a transcript from one university-->
                <!--NOTE: The structure of the ApplicantProfile XML can handle more universities though-->
                <xsl:for-each select="document('../../../../output_xml/OutputTranscript.xml')/transcript/degreetranscript">
                    <xsl:element name="ap:Degree">
                        <xsl:element name="ap:School">
                            <xsl:value-of select="document('../../../../output_xml/OutputTranscript.xml')/transcript/schoolname" />
                        </xsl:element>
                        <xsl:element name="ap:DegreeName">
                            <xsl:value-of select="degree" />
                        </xsl:element>
                        <xsl:element name="ap:Graduated">
                            <xsl:value-of select="graduated" />
                        </xsl:element>
                        <xsl:element name="ap:AverageGrade">
                            <xsl:value-of select="sum(course/gradedecimal) div count(course)"/>
                        </xsl:element>
                        <xsl:element name="ap:Transcript">
                            <xsl:attribute name="date">
                                <xsl:value-of select="document('../../../../output_xml/OutputTranscript.xml')/transcript/date" />
                            </xsl:attribute>
                            <xsl:for-each select="course">
                                <xsl:element name="ap:Course">
                                    <xsl:element name="ap:Name">
                                        <xsl:value-of select="name" />
                                    </xsl:element>
                                    <xsl:element name="ap:Grade">
                                        <xsl:value-of select="grade" />
                                    </xsl:element>
                                    <xsl:element name="ap:GradeDecimal">
                                        <xsl:value-of select="gradedecimal" />
                                    </xsl:element>
                                </xsl:element>
                            </xsl:for-each>
                        </xsl:element>
                    </xsl:element>
                </xsl:for-each>
            </xsl:element>
            <xsl:element name="ap:WorkExperience">
                <xsl:for-each select="document('../../../../output_xml/OutputEmploymentRecord.xml')/employmentrecord/employment">
                    <!--Save company name as variable to reference other XML files with-->
                    <xsl:param name="companyName">
                        <xsl:value-of select="companyname" />
                    </xsl:param>
                    <xsl:element name="ap:Employment">
                        <xsl:element name="ap:Company">
                            <xsl:element name="ap:Name">
                                <xsl:value-of select="companyname" />
                            </xsl:element>
                            <xsl:element name="ap:Address">
                                <!--Get address from CompanyInfo.xml-->
                                <xsl:value-of select="document('../../../../output_xml/OutputCompanyInfo.xml')/companies/companyinfo[name = $companyName]/address" />
                            </xsl:element>
                            <xsl:element name="ap:Telephone">
                                <!--Get telephone from CompanyInfo.xml-->
                                <xsl:value-of select="document('../../../../output_xml/OutputCompanyInfo.xml')/companies/companyinfo[name = $companyName]/phonenumber" />
                            </xsl:element>
                            <xsl:element name="ap:Email">
                                <!--Get email from CompanyInfo.xml-->
                                <xsl:value-of select="document('../../../../output_xml/OutputCompanyInfo.xml')/companies/companyinfo[name = $companyName]/email" />
                            </xsl:element>
                        </xsl:element>
                        <xsl:element name="ap:Description">
                            <xsl:element name="ap:Role">
                                <xsl:value-of select="roledescription" />
                            </xsl:element>
                            <xsl:element name="ap:StartDate">
                                <xsl:value-of select="startdate" />
                            </xsl:element>
                            <xsl:element name="ap:EndDate">
                                <xsl:value-of select="enddate" />
                            </xsl:element>
                        </xsl:element>
                    </xsl:element>
                </xsl:for-each>
            </xsl:element>
            <xsl:element name="ap:References">
                <xsl:for-each select="document('../../../../output_xml/OutputResume.xml')/resume/references">
                    <xsl:element name="ap:Contact">
                        <xsl:element name="ap:Name">
                            <xsl:value-of select="name" />
                        </xsl:element>
                        <xsl:element name="ap:CompanyName">
                            <xsl:value-of select="companyname" />
                        </xsl:element>
                        <xsl:element name="ap:Telephone">
                            <xsl:value-of select="contactinfo/telephonenumber" />
                        </xsl:element>
                        <xsl:element name="ap:Email">
                            <xsl:value-of select="contactinfo/email" />
                        </xsl:element>
                        <xsl:element name="ap:CanContact">
                            <xsl:value-of select="contactinfo/cancontact" />
                        </xsl:element>
                    </xsl:element>
                </xsl:for-each>
            </xsl:element>
        </xsl:element>
    </xsl:template>
</xsl:stylesheet>