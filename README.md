#### 1. Tomcat 서버를 시작할 때 웹 애플리케이션이 초기화하는 과정을 설명하라.
* 초기화 되는 과정은 처음으로 sql파일을 읽어 DataBase를 초기화 한다.
* 그런 후 DispatcherServlet을 통해 LoadOnStartup이 설정되어 서블릿 컨테이너가 시작할 때 인스턴스를 생성한다.
* 그러면 안의 init() 메서드가 실행되는데, 이는 RequestMapping객체를 통해 모든 URI를 Controller와 매핑한다.

#### 2. Tomcat 서버를 시작한 후 http://localhost:8080으로 접근시 호출 순서 및 흐름을 설명하라.
* 모든 접근은 ResourceFilter를 거치게 된다.
* 여기서 doFilter메소드가 실행되어 css와 같은 파일을 요청이 되면 defualt서블릿을 주게 된다.
* 그게 아니라면 우리가 커스텀한 웹서블릿을 요청하게 된다.
* 우리의 서블릿에서 service()가 실행되고, 이전에 Mapping해둔 uri와 Controller를 통해 서비스를 처리할 Controller를 가져온다.
* HomeController가 반환되고 여기의 execute()메소드가 실행된다.
* JspView를 반환하게 되고 이것을 home.jsp에 전달해 자바로서 HTML파일을 생성하고 클라이언트에게 보여준다.

#### 7. next.web.qna package의 ShowController는 멀티 쓰레드 상황에서 문제가 발생하는 이유에 대해 설명하라.
* 
