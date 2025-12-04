function validadorBusca() {
    let texto = document.getElementById('texto').value.trim();

    if (texto.length < 3) {
        alert("Digite pelo menos 3 caracteres para buscar.");
        return false;
    }
    return true;
}

window.addEventListener("DOMContentLoaded", () => {
    document.querySelectorAll(".card").forEach((el, i) => {
        el.style.animationDelay = `${i * 0.1}s`;
    });
});
