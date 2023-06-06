/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jenkinsci.plugins.job_strongauth_simple;

import org.htmlunit.html.HtmlElement;
import org.htmlunit.html.HtmlForm;
import org.htmlunit.html.HtmlInput;
import org.htmlunit.html.HtmlPage;
import hudson.model.Cause;
import hudson.model.FreeStyleBuild;
import hudson.model.FreeStyleProject;
import hudson.model.Hudson;
import hudson.model.Result;
import org.jvnet.hudson.test.HudsonTestCase;
import org.kohsuke.stapler.StaplerRequest;

/**
 *
 * @author kkkon
 */
public class JobStrongAuthSimpleBuilderLogicTest extends HudsonTestCase {

    private static final int LOG_LIMIT = 1000;

    public JobStrongAuthSimpleBuilderLogicTest() {
    }
    
    public void testConfig_empty() throws Exception
    {
        final FreeStyleProject prj = createFreeStyleProject();

        prj.getBuildersList().add( new JobStrongAuthSimpleBuilder( null, false, null, false, null ) );

        {
            final WebClient webClient = new WebClient();
            final HtmlPage p = webClient.getPage( prj, "configure" );
            assertNotNull( p );
            final HtmlForm f = p.getFormByName("config"); // !configure
            assertNotNull( f );

            submit(f);
        }

        final FreeStyleBuild build = prj.scheduleBuild2( 0, new Cause.UserCause() ).get();

        for ( final String log : prj.getLastBuild().getLog(LOG_LIMIT) )
        {
            System.out.println( log );
        }
        assertBuildStatus( Result.NOT_BUILT, build );
    }

    public void testConfig_jobUser1() throws Exception
    {

        hudson.setSecurityRealm(createDummySecurityRealm() );

        final FreeStyleProject prj = createFreeStyleProject();
        final String prjName = prj.getName();

        prj.getBuildersList().add( new JobStrongAuthSimpleBuilder( "alice,bob", false, null, false, null ) );

        {
            final WebClient webClient = new WebClient();
            final HtmlPage p = webClient.getPage( prj, "configure" );
            assertNotNull( p );
            final HtmlForm f = p.getFormByName("config"); // !configure
            assertNotNull( f );

            submit(f);
        }

        {
            final WebClient webClient = new WebClient().login( "alice", "alice" );

            final int number = prj.getNextBuildNumber();
            final HtmlPage p = webClient.getPage( prj, "build?delay=0sec" );

            final FreeStyleBuild build = prj.getBuildByNumber(number);
            while ( build.isBuilding() )
            {
                Thread.sleep( 3 * 1000 );
            }

            for ( final String log : prj.getLastBuild().getLog(LOG_LIMIT) )
            {
                System.out.println( log );
            }
            assertBuildStatus( Result.NOT_BUILT, build );
        }

        {
            final WebClient webClient = new WebClient().login( "bob", "bob" );

            final int number = prj.getNextBuildNumber();
            final HtmlPage p = webClient.getPage( prj, "build?delay=0sec" );

            final FreeStyleBuild build = prj.getBuildByNumber(number);
            while ( build.isBuilding() )
            {
                Thread.sleep( 3 * 1000 );
            }

            for ( final String log : prj.getLastBuild().getLog(LOG_LIMIT) )
            {
                System.out.println( log );
            }
            assertBuildStatus( Result.SUCCESS, build );
        }
    }

    public void testConfig_jobUser2() throws Exception
    {

        hudson.setSecurityRealm(createDummySecurityRealm() );

        final FreeStyleProject prj = createFreeStyleProject();
        final String prjName = prj.getName();

        prj.getBuildersList().add( new JobStrongAuthSimpleBuilder( "alice,charlie", false, null, false, null ) );

        {
            final WebClient webClient = new WebClient();
            final HtmlPage p = webClient.getPage( prj, "configure" );
            assertNotNull( p );
            final HtmlForm f = p.getFormByName("config"); // !configure
            assertNotNull( f );

            submit(f);
        }

        {
            final WebClient webClient = new WebClient().login( "alice", "alice" );

            final int number = prj.getNextBuildNumber();
            final HtmlPage p = webClient.getPage( prj, "build?delay=0sec" );

            final FreeStyleBuild build = prj.getBuildByNumber(number);
            while ( build.isBuilding() )
            {
                Thread.sleep( 3 * 1000 );
            }

            for ( final String log : prj.getLastBuild().getLog(LOG_LIMIT) )
            {
                System.out.println( log );
            }
            assertBuildStatus( Result.NOT_BUILT, build );
        }

        {
            final WebClient webClient = new WebClient().login( "bob", "bob" );

            final int number = prj.getNextBuildNumber();
            final HtmlPage p = webClient.getPage( prj, "build?delay=0sec" );

            final FreeStyleBuild build = prj.getBuildByNumber(number);
            while ( build.isBuilding() )
            {
                Thread.sleep( 3 * 1000 );
            }

            for ( final String log : prj.getLastBuild().getLog(LOG_LIMIT) )
            {
                System.out.println( log );
            }
            assertBuildStatus( Result.NOT_BUILT, build );
        }
        {
            final WebClient webClient = new WebClient().login( "charlie", "charlie" );

            final int number = prj.getNextBuildNumber();
            final HtmlPage p = webClient.getPage( prj, "build?delay=0sec" );

            final FreeStyleBuild build = prj.getBuildByNumber(number);
            while ( build.isBuilding() )
            {
                Thread.sleep( 3 * 1000 );
            }

            for ( final String log : prj.getLastBuild().getLog(LOG_LIMIT) )
            {
                System.out.println( log );
            }
            assertBuildStatus( Result.SUCCESS, build );
        }
    }

    public void testUseGlobalConfig_jobUser1() throws Exception
    {

        final FreeStyleProject prj = createFreeStyleProject();
        final String prjName = prj.getName();

        prj.getBuildersList().add( new JobStrongAuthSimpleBuilder( "charlie", true, null, false, null ) );
        {
            final String elementName = "JobStrongAuthSimplePlugin.users";

            final WebClient webClient = new WebClient();
            final HtmlPage p = webClient.goTo( "configure" );
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
                    input.setValue( "alice" );
                }
            }

            submit(f);
        }

        hudson.setSecurityRealm( createDummySecurityRealm() );

        {
            final WebClient webClient = new WebClient();
            final HtmlPage p = webClient.getPage( prj, "configure" );
            assertNotNull( p );
            final HtmlForm f = p.getFormByName("config"); // !configure
            assertNotNull( f );

            submit(f);
        }

        {
            final WebClient webClient = new WebClient().login( "alice", "alice" );

            final int number = prj.getNextBuildNumber();
            final HtmlPage p = webClient.getPage( prj, "build?delay=0sec" );

            final FreeStyleBuild build = prj.getBuildByNumber(number);
            while ( build.isBuilding() )
            {
                Thread.sleep( 3 * 1000 );
            }

            for ( final String log : prj.getLastBuild().getLog(LOG_LIMIT) )
            {
                System.out.println( log );
            }
            assertBuildStatus( Result.NOT_BUILT, build );
        }

        {
            final WebClient webClient = new WebClient().login( "bob", "bob" );

            final int number = prj.getNextBuildNumber();
            final HtmlPage p = webClient.getPage( prj, "build?delay=0sec" );

            final FreeStyleBuild build = prj.getBuildByNumber(number);
            while ( build.isBuilding() )
            {
                Thread.sleep( 3 * 1000 );
            }

            for ( final String log : prj.getLastBuild().getLog(LOG_LIMIT) )
            {
                System.out.println( log );
            }
            assertBuildStatus( Result.NOT_BUILT, build );
        }
        {
            final WebClient webClient = new WebClient().login( "charlie", "charlie" );

            final int number = prj.getNextBuildNumber();
            final HtmlPage p = webClient.getPage( prj, "build?delay=0sec" );

            final FreeStyleBuild build = prj.getBuildByNumber(number);
            while ( build.isBuilding() )
            {
                Thread.sleep( 3 * 1000 );
            }

            for ( final String log : prj.getLastBuild().getLog(LOG_LIMIT) )
            {
                System.out.println( log );
            }
            assertBuildStatus( Result.SUCCESS, build );
        }
    }

    public void testUseGlobalConfig_jobUser2() throws Exception
    {

        final FreeStyleProject prj = createFreeStyleProject();
        final String prjName = prj.getName();

        prj.getBuildersList().add( new JobStrongAuthSimpleBuilder( "bob,charlie", true, null, false, null ) );
        {
            final String elementName = "JobStrongAuthSimplePlugin.users";

            final WebClient webClient = new WebClient();
            final HtmlPage p = webClient.goTo( "configure" );
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
                    input.setValue( "charlie" );
                }
            }

            submit(f);
        }

        hudson.setSecurityRealm( createDummySecurityRealm() );

        {
            final WebClient webClient = new WebClient();
            final HtmlPage p = webClient.getPage( prj, "configure" );
            assertNotNull( p );
            final HtmlForm f = p.getFormByName("config"); // !configure
            assertNotNull( f );

            submit(f);
        }

        {
            final WebClient webClient = new WebClient().login( "alice", "alice" );

            final int number = prj.getNextBuildNumber();
            final HtmlPage p = webClient.getPage( prj, "build?delay=0sec" );

            final FreeStyleBuild build = prj.getBuildByNumber(number);
            while ( build.isBuilding() )
            {
                Thread.sleep( 3 * 1000 );
            }

            for ( final String log : prj.getLastBuild().getLog(LOG_LIMIT) )
            {
                System.out.println( log );
            }
            assertBuildStatus( Result.NOT_BUILT, build );
        }

        {
            final WebClient webClient = new WebClient().login( "bob", "bob" );

            final int number = prj.getNextBuildNumber();
            final HtmlPage p = webClient.getPage( prj, "build?delay=0sec" );

            final FreeStyleBuild build = prj.getBuildByNumber(number);
            while ( build.isBuilding() )
            {
                Thread.sleep( 3 * 1000 );
            }

            for ( final String log : prj.getLastBuild().getLog(LOG_LIMIT) )
            {
                System.out.println( log );
            }
            assertBuildStatus( Result.NOT_BUILT, build );
        }
        {
            final WebClient webClient = new WebClient().login( "charlie", "charlie" );

            final int number = prj.getNextBuildNumber();
            final HtmlPage p = webClient.getPage( prj, "build?delay=0sec" );

            final FreeStyleBuild build = prj.getBuildByNumber(number);
            while ( build.isBuilding() )
            {
                Thread.sleep( 3 * 1000 );
            }

            for ( final String log : prj.getLastBuild().getLog(LOG_LIMIT) )
            {
                System.out.println( log );
            }
            assertBuildStatus( Result.SUCCESS, build );
        }
    }

}
