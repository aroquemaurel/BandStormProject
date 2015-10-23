package bandstorm

import bandstorm.service.UserService
import grails.plugin.springsecurity.SpringSecurityService
import bandstorm.dao.UserDAOService
import grails.test.mixin.*
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.AuthenticationManager
import spock.lang.*

@TestFor(UserController)
@Mock(User)
class UserControllerSpec extends Specification {

    def populateValidParams(params) {
        assert params != null
        params["username"] = "toto"
        params["firstName"] = "titi"
        params["lastName"] = "tata"
        params["email"] = "toto@mail.com"
        params["country"] = "France"
        params["password"] = "123456"
        def calendar = Calendar.getInstance()
        calendar.set(1991,GregorianCalendar.SEPTEMBER,20)
        params["birthDate"] = calendar.getTime()
        params["urlAvatar"] = "http://toto.com"
    }

    void "Test the index action returns the correct model"() {

        when: "The index action is executed"
        controller.index()

        then: "The model is correct"
        !model.userInstanceList
        model.userInstanceCount == 0
    }

    void "Test the create action returns the correct model"() {
        when: "The create action is executed"
        controller.create()

        then: "The model is correctly created"
        model.userInstance != null
    }


    void "Test the passwordSettings action returns the correct model"() {

        given: "The security service for user is created"
        populateValidParams(params)
        User user = new User(params)
        user.save()
        controller.userService = Mock(UserService)
        controller.userService.springSecurityService >> Mock(SpringSecurityService) {
            getCurrentUser() >> user
        }

        when: "The passwordSettings action is executed without a parameter"
        controller.passwordSettings()

        then: "The model is correctly created"
        model.userInstance == user

        when: "the passwordSettings action is executed with a parameter"
        controller.passwordSettings(user)

        then: "the returned user is the one passed as a parameter"
        model.userInstance == user
    }


    void "Test the profilSettings action returns the correct model"() {

        given: "The security service for user is created"
        populateValidParams(params)
        User user = new User(params)
        user.save()
        controller.userService = Mock(UserService)
        controller.userService.springSecurityService >> Mock(SpringSecurityService) {
            getCurrentUser() >> user
        }

        when: "The profilSettings action is executed"
        controller.profilSettings(user)

        then: "The model is correctly created"
        model.userInstance != null
    }

    void "Test the save action correctly persists an instance"() {

        given: "The service dao for user is created"
        populateValidParams(params)
        User user = new User(params)
        user.save()
        controller.userDAOService = Mock(UserDAOService) {
            create(_) >> user
        }
        controller.userService = Mock(UserService) {
            setUserRole(_) >> true
        }

        when: "The save action is executed with an invalid instance"
        request.contentType = FORM_CONTENT_TYPE
        def userBad = new User()
        userBad.validate()
        controller.save(userBad)

        then: "The create view is rendered again with the correct model"
        model.userInstance != null
        view == 'create'

        when: "The save action is executed with a valid instance"
        response.reset()

        def userGood = new User(params)

        controller.save(userGood)

        then: "the success creation view is rendred"
        model.type == 'success'
        view == '/user/successCreation'
        User.count() == 1
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
        response.redirectedUrl == '/user/index'
        flash.message != null
    }

    void "test profile settings"() {

        given: "used entities"
        User user = new User(params)
        controller.userService = Mock(UserService)
        controller.userService.springSecurityService >> Mock(SpringSecurityService) {
            getCurrentUser() >> user
        }

        when: "we have a null object passed as a parameter"
        controller.passwordSettings(null)

        then: "the method returns the currently logged user"
        model.userInstance == user

        when: "we call the profile settings method with the created user"
        controller.profilSettings(user)

        then: "the response is the same created user"
        model.userInstance == user
    }

    void "test logout method"() {

        given :"the userService"
        controller.userService = Mock(UserService) {
            logout(_,_) >> true
        }

        when: "we call the logout method"
        controller.logout()

        then: "we are redirected to the main page"
        response.redirectedUrl == "/"
    }

    void "Test that the show action returns the correct model"() {
        when: "The show action is executed with a null domain"
        controller.show(null)

        then: "A 404 error is returned"
        response.status == 404

        when: "A domain instance is passed to the show action"
        populateValidParams(params)
        def user = new User(params)
        controller.show(user)

        then: "A model is populated containing the domain instance"
        model.userInstance == user
    }

    void "Test the update action performs an update on a valid domain instance"() {

        given: "The service dao for user is created"
        populateValidParams(params)
        User user = new User(params)
        user.save()
        controller.userDAOService = Mock(UserDAOService) {
            update(_) >> user
        }

        when: "Update is called for a domain instance that doesn't exist"
        request.contentType = FORM_CONTENT_TYPE
        controller.update(null,"profilSettings")

        then: "A 404 error is returned"
        response.redirectedUrl == '/user/index'
        flash.message != null


        when: "An invalid domain instance is passed to the update action"
        response.reset()
        def userBad = new User()
        userBad.validate()
        controller.update(userBad,"profilSettings")

        then: "The edit view is rendered again with the invalid instance"
        view == 'profilSettings'
        model.userInstance == userBad
    }

    void "update method test for a valid instance"() {

        when: "A valid domain instance is passed to the update action"
        populateValidParams(params)
        def userBad = new User(params).save(flush: true)
        controller.userDAOService = Mock(UserDAOService) {
            update(_) >> userBad
        }
        controller.update(userBad,"profilSettings")

        then: "A redirect is issues to the show action"
        response.redirectedUrl == "/user/profilSettings"
    }

    void "Test that the delete action deletes an instance if it exists"() {
        when: "The delete action is called for a null instance"
        request.contentType = FORM_CONTENT_TYPE
        controller.delete(null)

        then: "A 404 is returned"
        response.redirectedUrl == '/user/index'
        flash.message != null

        when: "A domain instance is created"
        response.reset()
        populateValidParams(params)
        def user = new User(params).save(flush: true)

        then: "It exists"
        User.count() == 1

        when: "The domain instance is passed to the delete action"
        controller.delete(user)

        then: "The instance is deleted"
        User.count() == 0
        response.redirectedUrl == '/user/index'
        flash.message != null
    }

    void "test userHome method"() {
        given: "a valid user instance"
        def user = new User(params)

        controller.userDAOService = Mock(UserDAOService) {
            create(_) >> user
        }
        controller.userService = Mock(UserService)
        controller.userService.authenticationManager = Mock(AuthenticationManager)
        controller.userService.springSecurityService >> Mock(SpringSecurityService)
        populateValidParams(params)


        when: "the userHome method is called with a user without a role"
        controller.userHome()

        then: "the authentification fails so the user is not redirected to the userHome view"
        view != 'userHome'

    }

    void "test index method with max param"() {

        when : "the index action is called with a defined max param"
        controller.index(200)

        then: "the index view is rendered and instance count is 5"
        params.max == 100

    }

    void "test url redirect method when user is logged in"() {
        given: "the user service"
        controller.userService = Mock(UserService)

        when: "the urlRedirect method is called"
        controller.userService.springSecurityService >> Mock(SpringSecurityService) {
            isLoggedIn() >> true
        }
        controller.urlRedirect()

        then: "the userHome action is called"
        response.redirectedUrl == '/user/userHome'

    }

    void "test url redirect method when user is not logged in"() {
        given: "the user service"
        controller.userService = Mock(UserService)

        when: "no user is logged in"
        controller.userService.springSecurityService >> Mock(SpringSecurityService) {
            isLoggedIn() >> false
        }
        controller.urlRedirect()

        then: "the user is redirected to the index page"
        response.redirectedUrl == '/index'
    }
}
