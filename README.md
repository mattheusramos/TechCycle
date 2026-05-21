# ♻️ TechCycle

Aplicativo mobile Android desenvolvido para ajudar usuários a encontrar pontos de coleta de lixo próximos, promovendo descarte correto e sustentabilidade.

---

## 📱 Funcionalidades

- 📍 Localização em tempo real do usuário
- 📏 Cálculo automático de distância até os pontos de coleta
- 📋 Listagem de pontos próximos (sem uso de mapa)
- 🔍 Busca por locais
- ⚙️ Tela de configurações
- 🎨 Interface moderna com Material Design

---

## 🚀 Tecnologias utilizadas

- Kotlin
- Android Studio
- Jetpack Compose
- Material 3
- GPS (Fused Location Provider)

---

## 🧠 Como funciona

O aplicativo utiliza o GPS do dispositivo para obter a localização atual do usuário e calcula a distância até os pontos de coleta cadastrados usando a fórmula de Haversine.

---

## 📂 Estrutura do projeto

om.trabalho.elixo
│
├── ui/
│ ├── screens/
│ ├── components/
│ ├── theme/
│
├── data/
├── utils/
│
├── MainActivity.kt

---

## ⚙️ Como executar

1. Clone o repositório: git clone https://github.com/mattheusramos/E-lixo.git

2. Abra no Android Studio

3. Execute o projeto em um emulador ou dispositivo físico

---

## ⚠️ Permissões

O app solicita:
- 📍 Localização (ACCESS_FINE_LOCATION)

---

## 🎯 Objetivo

Facilitar o acesso a pontos de coleta e incentivar práticas sustentáveis no dia a dia.

---

## 📌 Status

🚧 Pronto
