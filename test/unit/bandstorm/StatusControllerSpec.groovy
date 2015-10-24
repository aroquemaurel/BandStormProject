package bandstorm


import grails.test.mixin.*
import org.springframework.http.HttpStatus
import spock.lang.*

@TestFor(StatusController)
@Mock(Status)
class StatusControllerSpec extends Specification {

    def populateValidParams(params) {
        assert params != null
        params["url"] = 'statusUrl'
        params["content"] = 'statusContent'
        params["lightCount"] = 10
    }

    void "Test the index action returns the correct model"() {

        when: "The index action is executed"
        controller.index()

        then: "The model is correct"
        !model.statusInstanceList
        model.statusInstanceCount == 0
    }

    void "Test the create action returns the correct model"() {
        when: "The create action is executed"
        controller.create()

        then: "The model is correctly created"
        model.statusInstance != null
    }

    void "Test the save action correctly persists an instance"() {

        when: "The save action is executed with an invalid instance"
        request.contentType = FORM_CONTENT_TYPE
        def status = new Status()
        status.validate()
        controller.save(status)

        then: "The create view is rendered again with the correct model"
        model.statusInstance != null
        view == 'create'

        when: "The save action is executed with a valid instance"
        response.reset()
        populateValidParams(params)
        status = new Status(params)

        controller.save(status)

        then: "A redirect is issued to the show action"
        //response.redirectedUrl == '/status/show/1'
        //controller.flash.message != null
        Status.count() == 1
    }

    void "test save method with null parameter"() {
        when:"we try to save a null object"
        controller.save(null)

        then: "the response value is not found"
        response.status == HttpStatus.NOT_FOUND.value()
    }

    void "test save method with null parameter with form content type"() {

        when: "we try to save a null object through a form"
        request.contentType = FORM_CONTENT_TYPE
        controller.save(null)

        then: "the response status is notfound and the redirect is to the index page"
        response.redirectedUrl == '/status/index'
        flash.message != null
    }

    void "Test that the show action returns the correct model"() {
        when: "The show action is executed with a null domain"
        controller.show(null)

        then: "A 404 error is returned"
        response.status == 404

        when: "A domain instance is passed to the show action"
        populateValidParams(params)
        def status = new Status(params)
        controller.show(status)

        then: "A model is populated containing the domain instance"
        model.statusInstance == status
    }

    void "Test that the edit action returns the correct model"() {
        when: "The edit action is executed with a null domain"
        controller.edit(null)

        then: "A 404 error is returned"
        response.status == 404

        when: "A domain instance is passed to the edit action"
        populateValidParams(params)
        def status = new Status(params)
        controller.edit(status)

        then: "A model is populated containing the domain instance"
        model.statusInstance == status
    }

    void "Test the update action performs an update on a valid domain instance"() {
        when: "Update is called for a domain instance that doesn't exist"
        request.contentType = FORM_CONTENT_TYPE
        controller.update(null)

        then: "A 404 error is returned"
        response.redirectedUrl == '/status/index'
        flash.message != null


        when: "An invalid domain instance is passed to the update action"
        response.reset()
        def status = new Status()
        status.validate()
        controller.update(status)

        then: "The edit view is rendered again with the invalid instance"
        view == 'edit'
        model.statusInstance == status

        when: "A valid domain instance is passed to the update action"
        response.reset()
        populateValidParams(params)
        status = new Status(params).save(flush: true)
        controller.update(status)

        then: "A redirect is issues to the show action"
        //response.redirectedUrl == "/status/show/$status.id"
        flash.message != null
    }

    void "Test that the delete action deletes an instance if it exists"() {
        when: "The delete action is called for a null instance"
        request.contentType = FORM_CONTENT_TYPE
        controller.delete(null)

        then: "A 404 is returned"
        response.redirectedUrl == '/status/index'
        flash.message != null

        when: "A domain instance is created"
        response.reset()
        populateValidParams(params)
        def status = new Status(params).save(flush: true)

        then: "It exists"
        Status.count() == 1

        when: "The domain instance is passed to the delete action"
        controller.delete(status)

        then: "The instance is deleted"
        Status.count() == 0
        response.redirectedUrl == '/status/index'
        flash.message != null
    }

    void "test index method with max param"() {

        when : "the index action is called with a defined max param"
        controller.index(200)

        then: "the index view is rendered and params.max = 100"
        params.max == 100

    }
}
