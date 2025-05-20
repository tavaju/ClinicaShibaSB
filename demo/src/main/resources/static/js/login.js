document.addEventListener("DOMContentLoaded", function () {
  const togglePassword = document.querySelector(".toggle-password");
  const passwordInput = document.querySelector("#password");
  const loginForm = document.querySelector("form[action='/login']");

  togglePassword.addEventListener("click", function () {
    const type =
      passwordInput.getAttribute("type") === "password" ? "text" : "password";
    passwordInput.setAttribute("type", type);

    this.classList.toggle("fa-eye-slash");
    this.classList.toggle("fa-eye");
  });

  // Interceptar el envío del formulario para manejar la autenticación con JWT
  if (loginForm) {
    loginForm.addEventListener("submit", function(event) {
      event.preventDefault();
      
      const email = document.getElementById("email").value;
      const password = document.getElementById("password").value;
      
      // Realizar la solicitud de autenticación
      fetch('/auth/login', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify({
          email: email,
          password: password
        })
      })
      .then(response => {
        if (!response.ok) {
          throw new Error('Credenciales inválidas');
        }
        return response.text(); // El token viene como texto plano
      })
      .then(token => {
        // Guardar el token en localStorage
        localStorage.setItem('jwtToken', token);
        localStorage.setItem('username', email);
        
        // Obtener el rol del usuario
        return fetch('/auth/validate', {
          method: 'GET',
          headers: {
            'Authorization': `Bearer ${token}`
          }
        });
      })
      .then(response => response.json())
      .then(data => {        // Redireccionar según el rol
        switch (data.role) {
          case 'ADMIN':
            window.location.href = '/cliente/all';
            break;
          case 'VET':
            window.location.href = '/mascota/edit';
            break;
          case 'CLIENT':
            window.location.href = `/cliente-view/perfil?correo=${data.username}`;
            break;
          default:
            window.location.href = '/';
            break;
        }
      })
      .catch(error => {
        console.error('Error durante el inicio de sesión:', error);
        // Mostrar mensaje de error
        const errorMessage = document.querySelector(".error-message");
        if (errorMessage) {
          errorMessage.textContent = 'Credenciales inválidas. Por favor intente nuevamente.';
          errorMessage.style.display = 'block';
        } else {
          // Si no existe el elemento de error, crear uno nuevo
          const formContainer = document.querySelector(".form-container");
          const newError = document.createElement("div");
          newError.className = "error-message";
          newError.style.cssText = "color: #ff3860; text-align: center; margin: 10px 0; padding: 10px; border-radius: 4px; background-color: #feecf0; font-size: 14px;";
          newError.textContent = 'Credenciales inválidas. Por favor intente nuevamente.';
          formContainer.insertBefore(newError, loginForm);
        }
      });
    });
  }
});
