// --- Lógica do Relógio (Igual aos outros arquivos) ---
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

// --- Lógica do Formulário de Cadastro ---

function toggleRegPassword() {
    const passwordInput = document.getElementById('regPasswordInput');
    const eyeIcon = document.getElementById('regEyeIcon');
    
    if (passwordInput.type === 'password') {
        passwordInput.type = 'text';
        passwordInput.classList.add('text-azul-escuro');
    } else {
        passwordInput.type = 'password';
        passwordInput.classList.remove('text-azul-escuro');
    }
}

document.addEventListener('DOMContentLoaded', () => {
    updateClock();
    setInterval(updateClock, 1000);
});