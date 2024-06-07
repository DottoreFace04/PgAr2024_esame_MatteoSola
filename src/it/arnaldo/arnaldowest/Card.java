package it.arnaldo.arnaldowest;

public enum Card {
    BANG,
    MISSED,
    
    SCHOFIELD(2), 
    REMINGTON(3),
    REV_CARABINE(4),
    WINCHESTER(5);

    private final Integer dist; // Uso Integer per poter gestire null

    // Costruttore per carte senza portata
    Card() {
        this.dist = null;
    }

    // Costruttore per carte con portata
    Card(int dist) {
        this.dist = dist;
    }

    // Metodo per ottenere la portata della carta
    public Integer get_portata() {
        return dist;
   
    }
}
