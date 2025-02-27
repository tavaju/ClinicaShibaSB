// Array de testimonios de prueba
const testimonials = [
  {
    image:
      "https://images.unsplash.com/photo-1553322378-eb94e5966b0c?q=80&w=2940&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
    text: "Son muy buenos, gracias por la ayuda, logramos resolver la urgencia de nuestro pequeño, y todo salió bien finalmente. También tienen una página web increíble, super fácil de usar y muy linda para ser una veterinaria :)",
    author: "Viviana Suárez",
    pet: "con su mascota Toby",
    rating: 5,
  },
  {
    image:
      "https://images.unsplash.com/photo-1598681244895-1786022fe447?q=80&w=2940&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
    text: "Excelente atención, el Dr. Martínez fue muy profesional y cariñoso con mi gato. Las instalaciones son modernas y limpias.",
    author: "Carlos Mendoza",
    pet: "con su mascota Luna",
    rating: 5,
  },
  {
    image:
      "https://plus.unsplash.com/premium_photo-1665296633338-f4fb190f71ef?q=80&w=2938&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
    text: "Mi perro recibió un tratamiento excepcional. El personal es muy atento y las consultas son muy completas. Recomiendo totalmente esta clínica.",
    author: "Miguel Ángel Pérez",
    pet: "con su mascota Max",
    rating: 4,
  },
];

// Variables para controlar el carrusel
let currentTestimonialIndex = 0;

// Elementos del DOM
const prevButton = document.querySelector(".carousel-arrow.prev");
const nextButton = document.querySelector(".carousel-arrow.next");
const dots = document.querySelectorAll(".carousel-dots .dot");

// Función para generar estrellas HTML
function generateStarsHTML(rating) {
  let starsHTML = "";
  for (let i = 1; i <= 5; i++) {
    if (i <= rating) {
      starsHTML +=
        '<svg class="star" xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><polygon points="12 2 15.09 8.26 22 9.27 17 14.14 18.18 21.02 12 17.77 5.82 21.02 7 14.14 2 9.27 8.91 8.26 12 2"></polygon></svg>';
    } else {
      starsHTML +=
        '<svg class="star empty" xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><polygon points="12 2 15.09 8.26 22 9.27 17 14.14 18.18 21.02 12 17.77 5.82 21.02 7 14.14 2 9.27 8.91 8.26 12 2"></polygon></svg>';
    }
  }
  return starsHTML;
}

// Función para actualizar el contenido del testimonio
function updateTestimonial() {
  const testimonial = testimonials[currentTestimonialIndex];
  const testimonialCard = document.querySelector(".testimonial-card");

  // Añadir clase para fade out
  testimonialCard.classList.add("fade-out");

  // Esperar a que termine la animación de fade out
  setTimeout(() => {
    // Actualizar contenido
    testimonialCard.innerHTML = `
            <div class="testimonial-image-container">
                <div class="image-background"></div>
                <img src="${
                  testimonial.image
                }" alt="Testimonial" class="testimonial-image">
            </div>
            <div class="testimonial-content">
                <div class="rating">
                    ${generateStarsHTML(testimonial.rating)}
                </div>
                <p class="testimonial-text">"${testimonial.text}"</p>
                <p class="testimonial-author">${testimonial.author}</p>
                <p class="testimonial-pet">${testimonial.pet}</p>
            </div>
        `;

    // Actualizar dots
    dots.forEach((dot, index) => {
      dot.classList.toggle("active", index === currentTestimonialIndex);
    });

    // Añadir clase para fade in
    testimonialCard.classList.remove("fade-out");
    testimonialCard.classList.add("fade-in");
  }, 300);
}

// Event listeners para los botones de navegación
prevButton.addEventListener("click", () => {
  currentTestimonialIndex =
    (currentTestimonialIndex - 1 + testimonials.length) % testimonials.length;
  updateTestimonial();
});

nextButton.addEventListener("click", () => {
  currentTestimonialIndex = (currentTestimonialIndex + 1) % testimonials.length;
  updateTestimonial();
});

// Event listeners para los dots
dots.forEach((dot, index) => {
  dot.addEventListener("click", () => {
    currentTestimonialIndex = index;
    updateTestimonial();
  });
});

// Inicializar el primer testimonio
updateTestimonial();

// Variables for blog carousel
let currentBlogIndex = 0;
const blogCards = document.querySelectorAll(".blog-card");
const totalBlogs = blogCards.length;

// Function to update visible blog entries
function updateBlogCarousel() {
  blogCards.forEach((card, index) => {
    // Calculate the index of the card to display
    const displayIndex = (currentBlogIndex + index) % totalBlogs;
    card.style.display = displayIndex < 3 ? "block" : "none";
    card.style.order = displayIndex; // Use order to rearrange the cards
  });
}

// Event listeners for blog navigation buttons
document.querySelector(".blog-nav .prev").addEventListener("click", () => {
  currentBlogIndex = (currentBlogIndex - 1 + totalBlogs) % totalBlogs;
  updateBlogCarousel();
});

document.querySelector(".blog-nav .next").addEventListener("click", () => {
  currentBlogIndex = (currentBlogIndex + 1) % totalBlogs;
  updateBlogCarousel();
});

updateBlogCarousel();

// Función para manejar el scroll
function handleScroll() {
    const navbar = document.querySelector('.navbar');
    const announcement = document.querySelector('.announcement-banner');
    const scrollPosition = window.scrollY;
    
    // Añadir efecto glassmorphism a la navbar
    if (scrollPosition > 0) {
        navbar.classList.add('scrolled');
    } else {
        navbar.classList.remove('scrolled');
    }
    
    // Ocultar announcement banner
    if (scrollPosition > 100) {
        announcement.classList.add('hide');
    } else {
        announcement.classList.remove('hide');
    }
}

// Agregar event listener para el scroll
window.addEventListener('scroll', handleScroll);

let lastScrollTop = 0;
const navbar = document.querySelector('.navbar');
const banner = document.querySelector('.announcement-banner');

window.addEventListener('scroll', () => {
    // Para el navbar
    if (window.scrollY > 10) {
        navbar.classList.add('scrolled');
    } else {
        navbar.classList.remove('scrolled');
    }

    // Para el banner
    const currentScroll = window.pageYOffset || document.documentElement.scrollTop;
    if (currentScroll > lastScrollTop && currentScroll > 50) {
        // Scrolling down & past 50px
        banner.classList.add('hide');
    } else if (currentScroll < lastScrollTop) {
        // Scrolling up
        banner.classList.remove('hide');
    }
    lastScrollTop = currentScroll <= 0 ? 0 : currentScroll;
});


function handleSubmit(event) {
  event.preventDefault();
  
  const formData = {
      nombre: document.getElementById('nombre').value,
      email: document.getElementById('email').value,
      telefono: document.getElementById('telefono').value,
      tipoMascota: document.getElementById('tipoMascota').value,
      mensaje: document.getElementById('mensaje').value
  };

  const formStatus = document.getElementById('formStatus');
  showStatus('success', '¡Mensaje enviado con éxito! Nos pondremos en contacto contigo pronto.');
  document.getElementById('contactForm').reset();
}

function showStatus(type, message) {
  const formStatus = document.getElementById('formStatus');
  formStatus.textContent = message;
  formStatus.className = `form-status ${type}`;
  
  // Ocultar el mensaje después de 5 segundos
  setTimeout(() => {
      formStatus.style.display = 'none';
  }, 5000);
}

