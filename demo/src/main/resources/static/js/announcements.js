document.addEventListener("DOMContentLoaded", () => {
  const announcements = [
    "🎉 ¡Nuevo servicio de grooming disponible!",
    "🏥 Consultas de emergencia 24/7",
    "💉 20% de descuento en vacunas este mes",
  ];
  let currentAnnouncementIndex = 0;

  const announcementText = document.querySelector(".announcement-text");
  const leftArrow = document.querySelector(".banner-arrow.left");
  const rightArrow = document.querySelector(".banner-arrow.right");

  const updateAnnouncement = () => {
    announcementText.textContent = announcements[currentAnnouncementIndex];
  };

  leftArrow.addEventListener("click", () => {
    currentAnnouncementIndex =
      currentAnnouncementIndex === 0
        ? announcements.length - 1
        : currentAnnouncementIndex - 1;
    updateAnnouncement();
  });

  rightArrow.addEventListener("click", () => {
    currentAnnouncementIndex =
      (currentAnnouncementIndex + 1) % announcements.length;
    updateAnnouncement();
  });

  setInterval(() => {
    currentAnnouncementIndex =
      (currentAnnouncementIndex + 1) % announcements.length;
    updateAnnouncement();
  }, 3000);
});

// Agregar el manejo del click para el perfil
document.addEventListener("DOMContentLoaded", function () {
  const profileContainer = document.querySelector(".profile-container");

  // Toggle dropdown al hacer click
  profileContainer.addEventListener("click", function (e) {
    this.classList.toggle("active");
  });

  // Cerrar dropdown al hacer click fuera
  document.addEventListener("click", function (e) {
    if (!profileContainer.contains(e.target)) {
      profileContainer.classList.remove("active");
    }
  });
});
