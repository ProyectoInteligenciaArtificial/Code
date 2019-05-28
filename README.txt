1.1.a) ready
1.1.b) ready
1.1.c) implementar interfaz, se puede usar Map::getTerrains() para obtener los terrenos y editar los atributos.
1.2.d) implementar en interfaz, se puede usar Map::getTerrain(Map::getCell(char, int)).getName() para obtener el nombre del terreno.
1.3.e) implementar en interfaz, se puede utilizar Map::setInitialState(State(char, int)) y Map::setFinalState(State(char, int)).
1.4.f) ready
2.g) Implementar interfaz para agregar y personalizar seres. puede utilizarse Map::addPlayer(Player) para agregar un ser al mapa, Map::RemovePlayer(Player) si es necesario removerlo.
2.h) implementar interfaz para elegir y posicionar al ser, puede utilizarse Map::setSelectedPlayer(int)
2.i) ready
2.j) implementar interfaz para informar si se ha alcanzado el estado final, puede compararse con Map::getCurrentState() y Map::getFinalState()

Considerar:
- Hay métodos que retornan excepciones. Se debe revisarlos para capturarlas y no se detenga la aplicación.
- El método Map::getMap() retorna una imagen BufferedImage con toda la información de cada coordenada, terrenos, visitas, estado inicial y final y el jugado que se está utilizando.
- Los ComponentListener y KeyListener deben dejarse como están. Si se utiliza otro JFrame para mostrar la imagen del mapa debe de implementarse lo Listeners sobre el que se utilice.
- La interfaz gráfica debe separarse de la infraestructura lógica de la aplicación.
