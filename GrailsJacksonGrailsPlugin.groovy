import org.codehaus.groovy.grails.web.servlet.mvc.ParameterCreationListener

import com.hornmicro.JackSONParsingParameterCreationListener

class GrailsJacksonGrailsPlugin {
    // the plugin version
    def version = "0.1"
    // the version or versions of Grails the plugin is designed for
    def grailsVersion = "2.1 > *"
    // the other plugins this plugin depends on
    def dependsOn = [:]
    // resources that are excluded from plugin packaging
    def pluginExcludes = [
        "grails-app/views/error.gsp"
    ]
    def loadAfter = ['converters']

    // TODO Fill in these fields
    def title = "Grails Jackson Plugin" // Headline display name of the plugin
    def author = "Scott Horn"
    def authorEmail = "scott@hornmicro.com"
    def description = '''\
Simple plugin to add render as JackSON for JSON output and replace the default grails JSON converter with jackson when urlmappings has parseRequest:true
'''

    // URL to the plugin's documentation
    def documentation = "http://grails.org/plugin/grails-jackson"

    // Extra (optional) plugin metadata

    // License: one of 'APACHE', 'GPL2', 'GPL3'
    def license = "APACHE"

    // Details of company behind the plugin (if there is one)
//    def organization = [ name: "My Company", url: "http://www.my-company.com/" ]

    // Any additional developers beyond the author specified above.
//    def developers = [ [ name: "Joe Bloggs", email: "joe@bloggs.net" ]]

    // Location of the plugin's issue tracker.
//    def issueManagement = [ system: "JIRA", url: "http://jira.grails.org/browse/GPMYPLUGIN" ]

    // Online location of the plugin's browseable source code.
    def scm = [ url: "https://github.com/sjhorn/grails-jackson.git" ]

    def doWithWebDescriptor = { xml ->
        // TODO Implement additions to web.xml (optional), this event occurs before
    }

    def doWithSpring = {
        
        // Override default json request parsing
        jsonParsingParameterCreationListener(JackSONParsingParameterCreationListener)
    }

    def doWithDynamicMethods = { ctx ->
        
    }

    def doWithApplicationContext = { applicationContext ->
        // TODO Implement post initialization spring config (optional)
    }

    def onChange = { event ->
        // TODO Implement code that is executed when any artefact that this plugin is
        // watching is modified and reloaded. The event contains: event.source,
        // event.application, event.manager, event.ctx, and event.plugin.
    }

    def onConfigChange = { event ->
        // TODO Implement code that is executed when the project configuration changes.
        // The event is the same as for 'onChange'.
    }

    def onShutdown = { event ->
        // TODO Implement code that is executed when the application shuts down (optional)
    }
}
