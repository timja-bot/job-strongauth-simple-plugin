/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jenkinsci.plugins.job_strongauth_simple;

import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import hudson.model.FreeStyleProject;
import org.jvnet.hudson.test.HudsonTestCase;

/**
 *
 * @author kkkon
 */
public class JobStrongAuthSimpleBuilderTest extends HudsonTestCase {
    
    public JobStrongAuthSimpleBuilderTest() {
    }
    
    public void testGlobalConfig_empty() throws Exception
    {
        final WebClient webClient = new WebClient();
        final HtmlPage p = webClient.goTo("configure");
        assertNotNull( p );
        final HtmlForm f = p.getFormByName("config"); // !configure
        assertNotNull( f );
        submit(f);
    }

    public void testGlobalConfig_expireTimeInHours() throws Exception
    {
        final Integer inputValue = 8;
        final String elementName = "JobStrongAuthSimplePlugin.expireTimeInHours";
        {
            final WebClient webClient = new WebClient();
            final HtmlPage p = webClient.goTo("configure");
            assertNotNull( p );
            final HtmlForm f = p.getFormByName("config"); // !configure
            assertNotNull( f );

            {
                final HtmlElement e = p.getElementByName(elementName);
                assertNotNull( e );
                assertTrue( e instanceof HtmlInput );
                if ( e instanceof HtmlInput )
                {
                    final HtmlInput input = (HtmlInput)e;
                    input.setValueAttribute( inputValue.toString() );
                }
            }

            submit(f);

            // check
            final Integer expireTimeInHours = get(JobStrongAuthSimpleBuilder.DescriptorImpl.class).getExpireTimeInHours();
            assertNotNull( expireTimeInHours );
            assertEquals( inputValue, expireTimeInHours );
        }


        {
            final WebClient webClient = new WebClient();
            final HtmlPage p = webClient.goTo("configure");
            assertNotNull( p );
            final HtmlForm f = p.getFormByName("config"); // !configure
            assertNotNull( f );

            {
                final HtmlElement e = p.getElementByName(elementName);
                assertNotNull( e );
                assertTrue( e instanceof HtmlInput );
                if ( e instanceof HtmlInput )
                {
                    final HtmlInput input = (HtmlInput)e;

                    final String value = input.getValueAttribute();
                    assertNotNull( value );
                    assertEquals( inputValue.toString(), value );
                }
            }

            submit(f);

            // check
            {
                final HtmlElement e = p.getElementByName(elementName);
                assertNotNull( e );
                assertTrue( e instanceof HtmlInput );
                if ( e instanceof HtmlInput )
                {
                    final HtmlInput input = (HtmlInput)e;

                    final String value = input.getValueAttribute();
                    assertNotNull( value );
                    assertEquals( inputValue.toString(), value );
                }
            }
        }

    }

    public void testGlobalConfig_users() throws Exception
    {
        final String inputValue = "alice, bob";
        final String elementName = "JobStrongAuthSimplePlugin.users";
        {
            final WebClient webClient = new WebClient();
            final HtmlPage p = webClient.goTo("configure");
            assertNotNull( p );
            final HtmlForm f = p.getFormByName("config"); // !configure
            assertNotNull( f );

            {
                final HtmlElement e = p.getElementByName(elementName);
                assertNotNull( e );
                assertTrue( e instanceof HtmlInput );
                if ( e instanceof HtmlInput )
                {
                    final HtmlInput input = (HtmlInput)e;
                    input.setValueAttribute( inputValue );
                }
            }

            submit(f);

            // check
            final String users = get(JobStrongAuthSimpleBuilder.DescriptorImpl.class).getUsers();
            assertNotNull( users );
            assertEquals( inputValue, users );
        }


        {
            final WebClient webClient = new WebClient();
            final HtmlPage p = webClient.goTo("configure");
            assertNotNull( p );
            final HtmlForm f = p.getFormByName("config"); // !configure
            assertNotNull( f );

            {
                final HtmlElement e = p.getElementByName(elementName);
                assertNotNull( e );
                assertTrue( e instanceof HtmlInput );
                if ( e instanceof HtmlInput )
                {
                    final HtmlInput input = (HtmlInput)e;

                    final String value = input.getValueAttribute();
                    assertNotNull( value );
                    assertEquals( inputValue, value );
                }
            }

            submit(f);

            // check
            {
                final HtmlElement e = p.getElementByName(elementName);
                assertNotNull( e );
                assertTrue( e instanceof HtmlInput );
                if ( e instanceof HtmlInput )
                {
                    final HtmlInput input = (HtmlInput)e;

                    final String value = input.getValueAttribute();
                    assertNotNull( value );
                    assertEquals( inputValue, value );
                }
            }
        }

    }

    public void testConfig_empty() throws Exception
    {
        FreeStyleProject prj = createFreeStyleProject();
        prj.getBuildersList().add( new JobStrongAuthSimpleBuilder(null,false,null,false,null) );

        final WebClient webClient = new WebClient();
        final HtmlPage p = webClient.getPage( prj, "configure");
        assertNotNull( p );
        final HtmlForm f = p.getFormByName("config"); // !configure
        assertNotNull( f );

        submit(f);
    }

    public void testGlobalConfig_jobUsers() throws Exception
    {
        FreeStyleProject prj = createFreeStyleProject();
        prj.getBuildersList().add( new JobStrongAuthSimpleBuilder(null,false,null,false,null) );

        final String inputValue = "alice, bob";
        final String elementName = "JobStrongAuthSimplePlugin.jobUsers";
        {
            final WebClient webClient = new WebClient();
            final HtmlPage p = webClient.getPage( prj, "configure");
            assertNotNull( p );
            final HtmlForm f = p.getFormByName("config"); // !configure
            assertNotNull( f );

            {
                final HtmlElement e = p.getElementByName(elementName);
                assertNotNull( e );
                assertTrue( e instanceof HtmlInput );
                if ( e instanceof HtmlInput )
                {
                    final HtmlInput input = (HtmlInput)e;
                    input.setValueAttribute( inputValue );
                }
            }

            submit(f);

            // check
            final String users = prj.getBuildersList().get(JobStrongAuthSimpleBuilder.class).getJobUsers();
            assertNotNull( users );
            assertEquals( inputValue, users );
        }


        {
            final WebClient webClient = new WebClient();
            final HtmlPage p = webClient.getPage( prj, "configure");
            assertNotNull( p );
            final HtmlForm f = p.getFormByName("config"); // !configure
            assertNotNull( f );

            {
                final HtmlElement e = p.getElementByName(elementName);
                assertNotNull( e );
                assertTrue( e instanceof HtmlInput );
                if ( e instanceof HtmlInput )
                {
                    final HtmlInput input = (HtmlInput)e;

                    final String value = input.getValueAttribute();
                    assertNotNull( value );
                    assertEquals( inputValue, value );
                }
            }

            submit(f);

            // check
            {
                final HtmlElement e = p.getElementByName(elementName);
                assertNotNull( e );
                assertTrue( e instanceof HtmlInput );
                if ( e instanceof HtmlInput )
                {
                    final HtmlInput input = (HtmlInput)e;

                    final String value = input.getValueAttribute();
                    assertNotNull( value );
                    assertEquals( inputValue, value );
                }
            }
        }

    }

    public void testGlobalConfig_jobMinAuthUserNum() throws Exception
    {
        FreeStyleProject prj = createFreeStyleProject();
        prj.getBuildersList().add( new JobStrongAuthSimpleBuilder(null,false,null,false,null) );

        final Integer inputValue = 8;
        final String elementName = "JobStrongAuthSimplePlugin.jobMinAuthUserNum";
        {
            final WebClient webClient = new WebClient();
            final HtmlPage p = webClient.getPage( prj, "configure" );
            assertNotNull( p );
            final HtmlForm f = p.getFormByName("config"); // !configure
            assertNotNull( f );

            {
                final HtmlElement e = p.getElementByName(elementName);
                assertNotNull( e );
                assertTrue( e instanceof HtmlInput );
                if ( e instanceof HtmlInput )
                {
                    final HtmlInput input = (HtmlInput)e;
                    input.setValueAttribute( inputValue.toString() );
                }
            }

            submit(f);

            // check
            final Integer value = prj.getBuildersList().get(JobStrongAuthSimpleBuilder.class).getJobMinAuthUserNum();
            assertNotNull( value );
            assertEquals( inputValue, value );
        }


        {
            final WebClient webClient = new WebClient();
            final HtmlPage p = webClient.getPage( prj, "configure" );
            assertNotNull( p );
            final HtmlForm f = p.getFormByName("config"); // !configure
            assertNotNull( f );

            {
                final HtmlElement e = p.getElementByName(elementName);
                assertNotNull( e );
                assertTrue( e instanceof HtmlInput );
                if ( e instanceof HtmlInput )
                {
                    final HtmlInput input = (HtmlInput)e;

                    final String value = input.getValueAttribute();
                    assertNotNull( value );
                    assertEquals( inputValue.toString(), value );
                }
            }

            submit(f);

            // check
            {
                final HtmlElement e = p.getElementByName(elementName);
                assertNotNull( e );
                assertTrue( e instanceof HtmlInput );
                if ( e instanceof HtmlInput )
                {
                    final HtmlInput input = (HtmlInput)e;

                    final String value = input.getValueAttribute();
                    assertNotNull( value );
                    assertEquals( inputValue.toString(), value );
                }
            }
        }

    }
        
}
