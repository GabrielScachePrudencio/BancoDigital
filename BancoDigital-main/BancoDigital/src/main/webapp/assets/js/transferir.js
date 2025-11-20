function validadorBusca() {
    let texto = document.getElementById("texto").value;

    if (texto !== '') {
        let regexCPF = /^[0-9]{3}\.[0-9]{3}\.[0-9]{3}\-[0-9]{2}$/;
        let regexEmail = /^[\w\.-]+@[\w\.-]+\.\w+$/;

        if (regexCPF.test(texto)) {
            return true;
        }
        else if (regexEmail.test(texto)) {
            return true;
        }
        else {
            return true;
        }
    }

    return false;
}
