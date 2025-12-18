const modal = document.getElementById('appModal');
const modalContent = document.getElementById('modalContent');
const modalBody = document.getElementById('modalBody');
const modalTitle = document.getElementById('modalTitle');
const modalActionButton = document.getElementById('modalActionButton');

const formCarona = document.getElementById('formCarona');
const formMotorista = document.getElementById('formMotorista');
const tabCarona = document.getElementById('tabCarona');
const tabMotorista = document.getElementById('tabMotorista');
let latAtual, lonAtual, latDestino, lonDestino; // No topo do arquivo

let markerOrigem = null;
let markerDestino = null;

let destinoAtual;



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
/*
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
}*/

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

        // ESSENCIAL: Recalcula o tamanho do mapa após ele se tornar visível
        setTimeout(() => {
            if (map) {
                map.invalidateSize();
                // Se já houver rota, centraliza nela ao abrir a aba
                if (rotaLayer) {
                    map.fitBounds(rotaLayer.getBounds(), { padding: [40, 40] });
                }
            }
        }, 200);
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


let map;
let marker;

// Inicializa o mapa
document.addEventListener("DOMContentLoaded", () => {
  map = L.map("map").setView([-22.90556, -47.06083], 13); // Campinas

  L.tileLayer("https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png", {
    attribution: "© OpenStreetMap"
  }).addTo(map);

  document.getElementById("destinationInput").addEventListener("keypress", (e) => {
    if (e.key === "Enter") {
      e.preventDefault();
      buscarDestino(1);
    }
  });
  document.getElementById("startingPositionInput").addEventListener("keypress", (e) => {
    if (e.key === "Enter") {
      e.preventDefault();
      buscarDestino(0);
    }
  });
  
  document.getElementById("enviarRota").addEventListener("click", () => {
  if (!latAtual || !latDestino) {
    alert("Escolha local de partida e destino antes de publicar a rota");
    return;
  }

  desenharRota(latAtual, lonAtual, latDestino, lonDestino);
});

});

function atualizarMapa() {
  if (markerOrigem && markerDestino) {
    const group = L.featureGroup([markerOrigem, markerDestino]);
    map.fitBounds(group.getBounds(), { padding: [40, 40] });

    desenharRota(latAtual, lonAtual, latDestino, lonDestino);
  }
}


// Captura ENTER no input

let localAtual
let destinoFinal
async function buscarDestino(type) {
    const input = type === 0
        ? document.getElementById("startingPositionInput")
        : document.getElementById("destinationInput");

    if (!input.value) return;

    try {
        const url = `https://nominatim.openstreetmap.org/search?format=json&q=${encodeURIComponent(input.value)}`;
        const response = await fetch(url);
        const data = await response.json();

        if (!data.length) {
            alert("Local não encontrado");
            return;
        }

        const lat = parseFloat(data[0].lat);
        const lon = parseFloat(data[0].lon);

        if (type === 0) {
            if (markerOrigem) markerOrigem.remove();
            latAtual = lat; // Atribui à variável global
            lonAtual = lon;
            markerOrigem = L.marker([lat, lon], {
                icon: L.icon({
                    iconUrl: "https://maps.gstatic.com/mapfiles/ms2/micons/green-dot.png",
                    iconSize: [32, 32]
                })
            }).addTo(map).bindPopup("Origem");
        } else {
            if (markerDestino) markerDestino.remove();
            latDestino = lat; // Atribui à variável global
            lonDestino = lon;
            markerDestino = L.marker([lat, lon], {
                icon: L.icon({
                    iconUrl: "https://maps.gstatic.com/mapfiles/ms2/micons/red-dot.png",
                    iconSize: [32, 32]
                })
            }).addTo(map).bindPopup("Destino");
        }

        // Se ambos existirem, desenha a rota e ajusta o zoom
        if (latAtual && latDestino) {
            desenharRota(latAtual, lonAtual, latDestino, lonDestino);
        } else {
            // Se só tem um marker, centraliza nele
            map.setView([lat, lon], 14);
        }
    } catch (error) {
        console.error("Erro na busca:", error);
    }
}



async function calcularRota(latOrigem, lonOrigem, latDestino, lonDestino) {
  const url = `https://router.project-osrm.org/route/v1/driving/` +
              `${lonOrigem},${latOrigem};${lonDestino},${latDestino}` +
              `?overview=full&geometries=geojson`;

  const response = await fetch(url);
  const data = await response.json();

  return data.routes[0];
}
function ajustarMapa() {
  if (markerOrigem && markerDestino) {
    const group = L.featureGroup([markerOrigem, markerDestino]);
    map.fitBounds(group.getBounds(), { padding: [40, 40] });
  }
}




let rotaLayer;

async function desenharRota(latO, lonO, latD, lonD) {
  const url = `https://router.project-osrm.org/route/v1/driving/` +
              `${lonO},${latO};${lonD},${latD}?overview=full&geometries=geojson`;

  const response = await fetch(url);
  const data = await response.json();

  if (!data.routes || !data.routes.length) {
    alert("Não foi possível calcular a rota");
    return;
  }

  if (rotaLayer) {
    rotaLayer.remove();
  }

  rotaLayer = L.geoJSON(data.routes[0].geometry, {
    style: {
      color: "#2563eb",
      weight: 5
    }
  }).addTo(map);

  // 🔥 ISSO É O QUE ESTAVA FALTANDO
  map.fitBounds(rotaLayer.getBounds(), { padding: [40, 40] });
}







/*
async function buscarDestino(type) {
  let markerOrigem = null;
  let markerDestino = null;

  const destino = document.getElementById("destinationInput").value;

  if (!local) return;

  const url = `https://nominatim.openstreetmap.org/search?format=json&q=${encodeURIComponent(local)}`;

  const response = await fetch(url);
  const data = await response.json();

  if (data.length === 0) {
    alert("Local não encontrado");
    return;
  }

  const lat = data[0].lat;
  const lon = data[0].lon;

  // Atualiza mapa
  if (marker) {
    marker.remove();
  }
  if(type === 0){
    marker = L.marker([lat, lon], {
      title: "Origem",
    icon: L.icon({
      iconUrl: "https://maps.gstatic.com/mapfiles/ms2/micons/green-dot.png",
      iconSize: [32, 32]
    })
    }).addTo(map)
      .bindPopup(local)
      .openPopup();
  
    map.setView([lat, lon], 15);
    
    // Enviar para backend
    destinoAtual= local;
    latAtual = lat;
    lonAtual = lon;
  }else if(type === 1){
    marker = L.marker([lat, lon], {
    title: "Destino",
    icon: L.icon({
      iconUrl: "https://maps.gstatic.com/mapfiles/ms2/micons/red-dot.png",
      iconSize: [32, 32]
    })
    }).addTo(map)
      .bindPopup(local)
      .openPopup();
  
    map.setView([lat, lon], 15);
    
    // Enviar para backend
    destinoFinal= local;
    latDestino = lat;
    lonDestino = lon;
  }

  
  //return [destino, lat, lon];
 
}
  */




function enviarDestinoParaBackend(nome, lat, lng) {
  /*fetch("http://localhost:8080/api/destino", {
    method: "POST",
    headers: {
      "Content-Type": "application/json"
    },
    body: JSON.stringify({
      nome,
      latitude: lat,
      longitude: lng
    })
  });*/

  desenharRota(latAtual,lonAtual, latDestino, lonDestino);
}

