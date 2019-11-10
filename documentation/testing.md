# Test de l'application
## Test des modèles
### Mise en place
Pour tester nos modèles, nous avons simplement fait des assert sur les valeurs des attributs afin de tester nos getter et setter. A noter que nous avons voulu factoriser le code de test au maximum pour des questions de lisibilité.
### Exemple
Voici la classe de test pour la classe modèle `User`:
```java
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestUser {
    private final String username = "admin";
    private final String password = "admin1234";
    private final long id = 1890000;

    User HelperCreateUser() {
        User user = User.builder()
                .username(username)
                .password(password)
                .id(id)
                .build();
        return user;
    }

    void HelperAssertUser(User user) {
        assertNotNull(user);
        assertEquals(username, user.getUsername());
        assertEquals(password, user.getPassword());
        assertEquals(id, user.getId());
    }

    @Test
    void itShouldBePossibleToCreateAUser() {
        User user = HelperCreateUser();
        HelperAssertUser(user);
    }

    @Test
    void itShouldBePossibleToSetAUser() {
        User user = User.builder().build();
        user.setId(id);
        user.setUsername(username);
        user.setPassword(password);
        HelperAssertUser(user);
    }

    @Test
    void itShouldBePossibleToHaveTwoUsersEqual() {
        User user = HelperCreateUser();
        User user2 = HelperCreateUser();
        assertEquals(true, user.equals(user2));
    }
}
```
### Procédure
Dans chaque test de modèle, nous avons définis des valeurs statiques dans la classe de test qui nous servent d'élément de comparaison. Nous avons également écrit une fonction qui crée l'objet de la classe (`HelperCreateUser`) que nous voulons tester et une fonction qui fait les assertions (`HelperAssertUser`). Nous trouvons que cela apporte une bonne lisibilité aux tests unitaires et une factorisation du code.  

De manière systématique nous testons pour chaque modèle:
+ Son constructeur
+ Ses setter
+ Son opérateur `equals`

## Test des servlets
### Mise en place
Pour tester nos servlets, nous avons utilisé Mockito pour s'affranchir de la construction complète de tous les objets. Nous avons également utilisé des fonctions nous permettant de factoriser le code.
### Example
Voici une partie de la classe de test du servlet `RegisterServlet`:
```java
package ch.heig.amt.project.one.presentation;

@ExtendWith(MockitoExtension.class)
public class TestRegisterServlet {
    @Mock
    User user;
    @Mock
    UsersManagerLocal usersManagerLocal;
    @Mock
    HttpServletRequest request;
    @Mock
    HttpServletResponse response;
    @Mock
    RequestDispatcher requestDispatcher;

    List<String> errors;
    RegisterServlet servlet;

    @BeforeEach
    public void setup() {
        errors = new ArrayList<>();
        servlet = new RegisterServlet();
    }

    void HelperDoPostInit(String username, String password1, String password2) {
        servlet.usersManagerLocal = usersManagerLocal;
        when(usersManagerLocal.findUserByUsername(username)).thenReturn(user);
        when(request.getParameter("username")).thenReturn(username);
        when(request.getParameter("password1")).thenReturn(password1);
        when(request.getParameter("password2")).thenReturn(password2);
    }

    void HelperDoPostInvalid() throws ServletException, IOException {
        when(request.getRequestDispatcher("/WEB-INF/pages/register.jsp")).thenReturn(requestDispatcher);

        servlet.doPost(request, response);

        verify(request, atLeastOnce()).setAttribute("errors", errors);
        verify(requestDispatcher, atLeastOnce()).forward(request, response);
    }

    @Test
    void doGet() throws ServletException, IOException {
        when(request.getRequestDispatcher("/WEB-INF/pages/register.jsp")).thenReturn(requestDispatcher);
        servlet.doGet(request, response);
        verify(requestDispatcher, atLeastOnce()).forward(request, response);
    }

    @Test
    void doPost() throws ServletException, IOException {
        HelperDoPostInit("Obyka", "password", "password");
        String username = request.getParameter("username");
        String password1 = request.getParameter("password1");
        when(usersManagerLocal.create(username, password1)).thenReturn(true);
        when(request.getContextPath()).thenReturn("AMT-project-one");

        servlet.doPost(request, response);

        verify(usersManagerLocal, atLeastOnce()).findUserByUsername(username);
        verify(usersManagerLocal, atLeastOnce()).create(username, password1);
        verify(response, atLeastOnce()).sendRedirect(request.getContextPath() + "/login");
    }

    @Test
    void doPostWithDuplicateUser() throws ServletException, IOException {
        HelperDoPostInit("Obyka", "password", "password");
        String username = request.getParameter("username");
        when(user.getUsername()).thenReturn(username);

        errors.add("Le compte existe déjà, veuillez-vous connecter");

        HelperDoPostInvalid();
    }

    @Test
    void doPostWithEmptyUsername() throws ServletException, IOException {
        HelperDoPostInit("", "password", "password");

        errors.add("Le nom d'utilisateur ne peut pas être vide");

        HelperDoPostInvalid();
    }

    @Test
    void doPostWithDifferentPassword() throws ServletException, IOException {
        HelperDoPostInit("Obyka", "password", "password2");

        errors.add("Les mots de passe doivent correspondrent");

        HelperDoPostInvalid();
    }
}
```
**Note:** Chaque objet n'étant pas de type primitif doit être mocké pour définir son comportement lors de l'appel de ses fonctions dans le servlet.
### Procédure
Nous avons testé si chaque champ du formulaire était vide et si le/les message(s) d'erreur correspondant étaient corrects. Nous avons également testé pour chaque servlet si la redirection était correcte ou non selon les arguments.  

Nous n'avons pas testé l'intégralité des servlets car nous avons remarqué une certaine redondance sur la manière d'écrire les tests Mockito.

## Test des DAOs
### Mise en place
Pour tester nos DAOs, nous avons utilisé Arquilian avec une infrastructure de test docker dediée. Pour chaque test, nous avons fait le choix de faire des transactions en ROLLBACK afin de ne pas écrire dans la base de données.
### Example
Voici une partie de la classe de test du DAO `UsersManager`:
```java
@RunWith(Arquillian.class)
@MavenBuild
@DeploymentParameters(testable = true)
public class TestUsersManager {
    @EJB
    UsersManagerLocal usersManagerLocal;

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void itShouldBePossibleToCreateAUser() throws DuplicateKeyException, SQLException {
        boolean created = usersManagerLocal.create("Obyka" + System.currentTimeMillis(), "password");

        assertTrue(created);
    }

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void itShouldBePossibleToHaveAValidConnection() throws DuplicateKeyException, SQLException {
        long currentTime = System.currentTimeMillis();
        boolean created = usersManagerLocal.create("Obyka" + currentTime, "password");
        boolean connected = usersManagerLocal.validConnection("Obyka" + currentTime, "password");

        assertTrue(connected);
        assertTrue(created);
    }

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void itShoulBePossibleToFindAUserByUsername() throws DuplicateKeyException, SQLException {
        long currentTime = System.currentTimeMillis();
        boolean created = usersManagerLocal.create("Obyka" + currentTime, "password");
        User user = usersManagerLocal.findUserByUsername("Obyka" + currentTime);

        assertTrue(created);
        assertEquals("Obyka" + currentTime, user.getUsername());
    }
}
```
### Procédure
Pour chaque test, nous créeons un nouvel objet afin d'avoir une indépendance. Nous testons seulement les méthodes CRUD de base dans nos DAOs. Nous n'avons pas testé le DAO `WatchingInfosManager` car suite au bug de conception remarqué (c.f [Bug connus](known_bugs.md)), nous n'avons pas trouvé pratique d'effectuer des tests.