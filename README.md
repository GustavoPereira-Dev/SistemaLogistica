# Sistema Logística (FrontEnd)

<b> [Versão Anterior (Desenvolvida ao decorrer das aulas)](https://github.com/GustavoPereira-Dev/Projs-Fatec-ADS/tree/main/LabESW/frota)

[Versão Seguinte (Com mais entidades, funcionalidades e API)](https://github.com/NicolasDomingos09/ControleLogistico)
</b>

## Enunciado
- Altere a entidade caminhão para ter o tamanho em Comprimento,  Largura,  Altura e Fator de Cubagem. Adicione um atributo derivado para a metragem cúbica com o fator de cubagem, Fator de cubagem para transporte rodoviário: 300 kg/m³.
- A empresa trabalhará com caixas de papelão padronizadas, que tem Comprimento,  Largura,  Altura e limite de peso.
- A transportadora cobrará por peso ou por caixa, dado que há produtos leves e pesados.
Deve-se calcular o peso cubado, para saber como será cobrado o transporte. 
- A fórmula completa é: Peso Cubado = Volume (m³) x Fator de Cubagem. 

  1. Compare o peso cubado com o peso real: O valor que determinará o custo do frete será sempre o maior entre o peso real da carga e o peso cubado.
  - Por que calcular a cubagem é importante?
   - Otimização de custos: As transportadoras usam o peso cubado para cobrar o frete, pois ele reflete o espaço que a carga ocupa no veículo. 
   - Eficiência logística: O cálculo ajuda a planejar o transporte e a usar o espaço do veículo de forma mais eficiente, evitando o transporte de cargas volumosas com baixo peso ou cargas pesadas que ocupam pouco espaço. 
   - Conformidade: Em alguns casos, o não cumprimento das regras de cubagem pode resultar em multas e apreensão da carga, além de aumentar o custo com combustível e manutenção dos veículos. 
- O cliente solicitará um transporte de um produto, que deverá ser medido e pesado. Crie uma solicitação de transporte, onde o cliente deve especificar o produto que será transportado. Ofereça as opções de caixas para ele. - O produto deverá caber em uma das caixas oferecidas pela empresa. 
Calcule o frete pelo produto, ou por peso ou por caixa. Não se esqueça que a empresa tem um valor a ser cobrado por quilômetros rodado. 
- Utilize uma API para o cálculo da distância como:
  - Google Maps;
  - Haversine;
  - Bing Maps;
  - OpenRouteService.
- Um fator importante no cálculo do frete é o pedágio, pois ele encarecerá o frete. Utilize APIs que conseguem calcular os pedágio, como:
  - Google Routes
  - HERE Technologies
  - Mapplus
  - OpenRouteService

<b>  Integrantes: @GustavoPereira-Dev (Revisão), [@Joaoftito (Models, Services, DTOs e Controllers iniciais)](https://github.com/Joaoftito), [@NicolasDomingos09 (API e SolicitaçãoService)](https://github.com/NicolasDomingos09) e [@LiaWyllde (API e SolicitaçãoService)](https://github.com/LiaWyllde) </b>
