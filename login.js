// --- Lógica do Relógio (Cópia idêntica para manter o tempo real) ---
const timeDisplay = document.getElementById('statusBarTime');

function updateClock() {
    const now = new Date();
    const timeString = new Intl.DateTimeFormat('pt-BR', {
        hour: '2-digit',
        minute: '2-digit',
        hour12: false,
        timeZone: 'America/Sao_Paulo' 
    }).format(now);
    
    if (timeDisplay) {
        timeDisplay.textContent = timeString;
    }
}

// --- Lógica de UI do Login ---

// Alternar visibilidade da senha
function togglePassword() {
    const passwordInput = document.getElementById('passwordInput');
    const eyeIcon = document.getElementById('eyeIcon');
    
    if (passwordInput.type === 'password') {
        passwordInput.type = 'text';
        // Ícone de olho fechado (opcional trocar o path se quiser, aqui mantive simples)
        passwordInput.classList.add('text-azul-escuro');
    } else {
        passwordInput.type = 'password';
        passwordInput.classList.remove('text-azul-escuro');
    }
}

// Inicialização
document.addEventListener('DOMContentLoaded', () => {
    updateClock();
    setInterval(updateClock, 1000);
});


// Exibe mensagem de erro caso ?erro=true esteja na URL
const params = new URLSearchParams(window.location.search);
if (params.get("erro") === "true") {
    const erroBox = document.getElementById("erroLogin");
    if (erroBox) erroBox.classList.remove("hidden");
}