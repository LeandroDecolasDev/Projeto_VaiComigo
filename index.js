const modal = document.getElementById('appModal');
const modalContent = document.getElementById('modalContent');
const modalBody = document.getElementById('modalBody');
const modalTitle = document.getElementById('modalTitle');
const modalActionButton = document.getElementById('modalActionButton');

const formCarona = document.getElementById('formCarona');
const formMotorista = document.getElementById('formMotorista');
const tabCarona = document.getElementById('tabCarona');
const tabMotorista = document.getElementById('tabMotorista');

// Elemento do relógio
const timeDisplay = document.getElementById('statusBarTime');

/**
 * Atualiza o horário exibido na barra de status com o horário real de Brasília/SP.
 * Executado a cada segundo para precisão.
 */
function updateClock() {
    const now = new Date();
    
    // Formata o horário no formato HH:MM
    const timeString = new Intl.DateTimeFormat('pt-BR', {
        hour: '2-digit',
        minute: '2-digit',
        hour12: false,
        timeZone: 'America/Sao_Paulo' // Fuso de Brasília
    }).format(now);
    
    if (timeDisplay) {
        timeDisplay.textContent = timeString;
    }
}

// Inicialização
document.addEventListener('DOMContentLoaded', () => {
    // Define a intenção inicial
    setIntent('rider');
    
    // Atualiza o relógio imediatamente ao carregar
    updateClock();
    
    // Atualiza o relógio a cada 1 segundo (1000ms) para garantir que o minuto vire na hora certa
    setInterval(updateClock, 1000); 
});

/**
 * Alterna entre os modos de formulário (Carona/Motorista).
 */
function setIntent(intent) {
    if (intent === 'rider') {
        formCarona.classList.remove('hidden');
        formMotorista.classList.add('hidden');
        tabCarona.classList.add('bg-azul-escuro', 'text-white');
        tabMotorista.classList.remove('bg-azul-escuro', 'text-white');
        tabMotorista.classList.add('text-gray-500');
    } else {
        formMotorista.classList.remove('hidden');
        formCarona.classList.add('hidden');
        tabMotorista.classList.add('bg-azul-escuro', 'text-white');
        tabCarona.classList.remove('bg-azul-escuro', 'text-white');
        tabCarona.classList.add('text-gray-500');
    }
}

/**
 * Controle do Modal
 */
function toggleModal(title = "Detalhes", action = 'ride') {
    if (modal.classList.contains('hidden')) {
        modalTitle.textContent = title;
        modal.classList.remove('hidden');
        modal.classList.add('flex');
        
        if (action === 'perfil') {
            modalBody.innerHTML = '<p>Aqui você veria suas informações de perfil, veículo (se motorista), e configurações da conta. Este é apenas um placeholder.</p>';
            modalActionButton.textContent = 'Editar Perfil';
            modalActionButton.onclick = () => alert('A tela de edição de perfil não está implementada.');
        } else {
            modalActionButton.textContent = 'Solicitar Vaga';
            modalActionButton.onclick = () => alert('Solicitação Enviada! Aguarde a aprovação do motorista.');
        }
        
        setTimeout(() => {
            modalContent.classList.remove('opacity-0', 'scale-95');
            modalContent.classList.add('opacity-100', 'scale-100');
        }, 10);
    } else {
        hideModal();
    }
}

function hideModal() {
    modalContent.classList.remove('opacity-100', 'scale-100');
    modalContent.classList.add('opacity-0', 'scale-95');
    setTimeout(() => {
        modal.classList.add('hidden');
        modal.classList.remove('flex');
    }, 300);
}

/**
 * Popula o modal com os detalhes da carona.
 */
function showRideDetail(id) {
    let details = {};
    if (id === 1) {
        details = {
            time: "Amanhã, 08:00",
            origin: "Av. Paulista, 1000",
            destination: "Campus Centro - Bloco C",
            driver: "Pedro S. (Administrativo)",
            seats: 3,
            detour: "2 Km",
            notes: "Saída pontual. Uso obrigatório de máscara."
        };
    } else if (id === 2) {
        details = {
            time: "Hoje, 18:30",
            origin: "Shopping Central - Estacionamento G3",
            destination: "Bairro da Liberdade - Praça",
            driver: "Ana L. (Estudante)",
            seats: 1,
            detour: "0 Km (Sem desvios)",
            notes: "Saio 18h30 em ponto. Não paro em pontos secundários. Apenas rota principal."
        };
    }

    modalBody.innerHTML = `
        <p><span class="font-semibold text-white">Horário:</span> ${details.time}</p>
        <p><span class="font-semibold text-white">Origem:</span> ${details.origin}</p>
        <p><span class="font-semibold text-white">Destino:</span> ${details.destination}</p>
        <p><span class="font-semibold text-white">Motorista:</span> ${details.driver}</p>
        <p><span class="font-semibold text-white">Vagas Disponíveis:</span> <span class="${details.seats > 1 ? 'text-green-400' : 'text-yellow-400'} font-bold">${details.seats}</span></p>
        <p><span class="font-semibold text-white">Máx. Desvio Aceito:</span> ${details.detour}</p>
        <p class="text-sm text-gray-400 pt-3 border-t border-gray-800"><span class="font-semibold text-white">Observações:</span> ${details.notes}</p>
        <p class="text-xs text-azul-escuro pt-2">O serviço é gratuito para alunos e funcionários. Custeado pelo campus.</p>
    `;

    toggleModal("Detalhes da Rota", 'ride');
}

/**
 * Função para simular um alerta
 */
function alert(message) {
    const container = document.getElementById('appContainer');
    let alertBox = document.getElementById('customAlert');

    if (!alertBox) {
        alertBox = document.createElement('div');
        alertBox.id = 'customAlert';
        alertBox.className = 'absolute top-1/2 left-1/2 transform -translate-x-1/2 -translate-y-1/2 bg-gray-800 p-4 rounded-xl shadow-2xl border border-azul-escuro text-white text-center transition duration-300 ease-out z-20 opacity-0 scale-75';
        container.appendChild(alertBox);
    }

    alertBox.innerHTML = `
        <p class="text-lg font-medium">${message}</p>
        <button onclick="closeAlert()" class="mt-3 px-4 py-1 bg-azul-escuro rounded-lg text-sm hover:bg-blue-700">OK</button>
    `;
    
    // Mostrar
    alertBox.style.display = 'block';
    setTimeout(() => {
        alertBox.classList.remove('opacity-0', 'scale-75');
        alertBox.classList.add('opacity-100', 'scale-100');
    }, 10);
    
    hideModal();
}

function closeAlert() {
    const alertBox = document.getElementById('customAlert');
    if (alertBox) {
        alertBox.classList.remove('opacity-100', 'scale-100');
        alertBox.classList.add('opacity-0', 'scale-75');
        setTimeout(() => {
            alertBox.style.display = 'none';
        }, 300);
    }
}