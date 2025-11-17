let modalDeletarAberto = false;
let modalEditarAberto = false;

function modalDeletar() {
    modalDeletarAberto = !modalDeletarAberto;

    document.getElementById("modalDeletar").style.display = 
        modalDeletarAberto ? "flex" : "none";
}

function fecharModalDeletar() {
    modalDeletarAberto = false;
    document.getElementById("modalDeletar").style.display = "none";
}


function modalEditar() {
    modalEditarAberto = !modalEditarAberto;

    document.getElementById("modalEditar").style.display = 
        modalEditarAberto ? "flex" : "none";
}

function fecharModalEditar() {
    modalEditarAberto = false;
    document.getElementById("modalEditar").style.display = "none";
}
