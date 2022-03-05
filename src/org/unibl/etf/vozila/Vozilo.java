package org.unibl.etf.vozila;

import org.unibl.etf.logger.MyLogger;
import org.unibl.etf.mapa.*;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class Vozilo extends Element implements Runnable{

    static {
        try {
            MyLogger.setup();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected String marka;
    protected String model;
    protected int godiste;
    protected long brzina;
    protected int smijer; // 1-gore; 0-dole;
    protected int put;

    public Vozilo(){}

    public Vozilo(String marka, String model, int godiste, long brzina, int smijer, int put){
        this.marka = marka;
        this.model = model;
        this.godiste = godiste;
        this.brzina = brzina;
        this.smijer = smijer;
        this.put = put;
    }

    @Override
    public void run(){
        if (this.put == 1 && this.smijer == 0) {
            while (true) {
                if (Mapa.matrix[0][21] instanceof PutCell && Mapa.vozilaPut1Smijer0.indexOf(this) == 0
                        && Mapa.matrix[0][21].element == null) {
                    this.setX(0);
                    this.setY(21);
                    try {
                        Mapa.setElement(Mapa.matrix[this.getX()][this.getY()], this);
                        Mapa.vozilaPut1Smijer0.remove(this);
                    } catch (InterruptedException e) {
                        Logger.getLogger(Logger.GLOBAL_LOGGER_NAME).log(Level.WARNING, e.fillInStackTrace().toString());
                    }
                    try {
                        kretanjePut1Smijer0();
                    } catch (InterruptedException e) {
                        Logger.getLogger(Logger.GLOBAL_LOGGER_NAME).log(Level.WARNING, e.fillInStackTrace().toString());
                    }
                    break;
                }
            }
        } else if (this.put == 1 && this.smijer == 1) {
            while (true) {
                if (Mapa.matrix[8][29] instanceof PutCell && Mapa.vozilaPut1Smijer1.indexOf(this) == 0
                        && Mapa.matrix[8][29].element == null) {
                    this.setX(8);
                    this.setY(29);
                    try {
                        Mapa.setElement(Mapa.matrix[this.getX()][this.getY()], this);
                        Mapa.vozilaPut1Smijer1.remove(this);
                    } catch (InterruptedException e) {
                        Logger.getLogger(Logger.GLOBAL_LOGGER_NAME).log(Level.WARNING, e.fillInStackTrace().toString());
                    }
                    try {
                        kretanjePut1Smijer1();
                    } catch (InterruptedException e) {
                        Logger.getLogger(Logger.GLOBAL_LOGGER_NAME).log(Level.WARNING, e.fillInStackTrace().toString());
                    }
                    break;
                }
            }
        } else if (this.put == 2 && this.smijer == 0) {
            while (true) {
                if (Mapa.matrix[13][0] instanceof PutCell && Mapa.vozilaPut2Smijer0.indexOf(this) == 0
                        && Mapa.matrix[13][0].element == null) {
                    this.setX(13);
                    this.setY(0);
                    try {
                        Mapa.setElement(Mapa.matrix[this.getX()][this.getY()], this);
                        Mapa.vozilaPut2Smijer0.remove(this);
                    } catch (InterruptedException e) {
                        Logger.getLogger(Logger.GLOBAL_LOGGER_NAME).log(Level.WARNING, e.fillInStackTrace().toString());
                    }
                    try {
                        kretanjePut2Smijer0();
                    } catch (InterruptedException e) {
                        Logger.getLogger(Logger.GLOBAL_LOGGER_NAME).log(Level.WARNING, e.fillInStackTrace().toString());
                    }
                    break;
                }
            }
        } else if (this.put == 2 && this.smijer == 1) {
            while (true) {
                if (Mapa.matrix[14][29] instanceof PutCell && Mapa.vozilaPut2Smijer1.indexOf(this) == 0
                        && Mapa.matrix[14][29].element == null) {
                    this.setX(14);
                    this.setY(29);
                    try {
                        Mapa.setElement(Mapa.matrix[this.getX()][this.getY()], this);
                        Mapa.vozilaPut2Smijer1.remove(this);
                    } catch (InterruptedException e) {
                        Logger.getLogger(Logger.GLOBAL_LOGGER_NAME).log(Level.WARNING, e.fillInStackTrace().toString());
                    }
                    try {
                        kretanjePut2Smijer1();
                    } catch (InterruptedException e) {
                        Logger.getLogger(Logger.GLOBAL_LOGGER_NAME).log(Level.WARNING, e.fillInStackTrace().toString());
                    }
                    break;
                }
            }
        } else if (this.put == 3 && this.smijer == 0) {
            while (true) {
                if (Mapa.matrix[29][20] instanceof PutCell && Mapa.vozilaPut3Smijer0.indexOf(this) == 0
                        && Mapa.matrix[29][20].element == null) {
                    this.setX(29);
                    this.setY(20);
                    try {
                        Mapa.setElement(Mapa.matrix[this.getX()][this.getY()], this);
                        Mapa.vozilaPut3Smijer0.remove(this);
                    } catch (InterruptedException e) {
                        Logger.getLogger(Logger.GLOBAL_LOGGER_NAME).log(Level.WARNING, e.fillInStackTrace().toString());
                    }
                    try {
                        kretanjePut3Smijer0();
                    } catch (InterruptedException e) {
                        Logger.getLogger(Logger.GLOBAL_LOGGER_NAME).log(Level.WARNING, e.fillInStackTrace().toString());
                    }
                    break;
                }
            }
        } else if (this.put == 3 && this.smijer == 1) {
            while (true) {
                if (Mapa.matrix[22][29] instanceof PutCell && Mapa.vozilaPut3Smijer1.indexOf(this) == 0
                        && Mapa.matrix[22][29].element == null) {
                    this.setX(22);
                    this.setY(29);
                    try {
                        Mapa.setElement(Mapa.matrix[this.getX()][this.getY()], this);
                        Mapa.vozilaPut3Smijer1.remove(this);
                    } catch (InterruptedException e) {
                        Logger.getLogger(Logger.GLOBAL_LOGGER_NAME).log(Level.WARNING, e.fillInStackTrace().toString());
                    }
                    try {
                        kretanjePut3Smijer1();
                    } catch (InterruptedException e) {
                        Logger.getLogger(Logger.GLOBAL_LOGGER_NAME).log(Level.WARNING, e.fillInStackTrace().toString());
                    }
                    break;
                }
            }
        }
    }

    private void kretanjePut1Smijer0() throws InterruptedException {
        while (this.getX() < 7) {
            spavaj();
            if (Mapa.matrix[this.getX() + 1][this.getY()] instanceof PutCell) {
                if(Mapa.matrix[this.getX() + 1][this.getY()].element != null){
                    synchronized (this){
                        Mapa.matrix[this.getX() + 1][this.getY()].vozilaWait.add(this);
                        wait();
                    }
                }
                if(Mapa.matrix[this.getX() + 1][this.getY()].element == null) {
                    Mapa.setElement(Mapa.matrix[this.getX()][this.getY()], null);
                    this.incrementX();
                    Mapa.setElement(Mapa.matrix[this.getX()][this.getY()], this);
                }
            } else if (Mapa.matrix[this.getX() + 1][this.getY()] instanceof PruzniPrelaz) {
                if(Mapa.matrix[this.getX() + 1][this.getY()].getCounter().intValue() != 0){
                    synchronized (this) {
                        PruzniPrelaz.waitingListAB0.add(this);
                        wait();
                    }
                }
                if(Mapa.matrix[this.getX() + 1][this.getY()].element == null) {
                    Mapa.setElement(Mapa.matrix[this.getX()][this.getY()], null);
                    this.incrementX();
                    Mapa.setElement(Mapa.matrix[this.getX()][this.getY()], this);
                }
            }
        }
        while (true) {
            spavaj();
            if (this.getY() == 29) {
                Mapa.setElement(Mapa.matrix[this.getX()][this.getY()], null);
                break;
            }
            if (Mapa.matrix[this.getX()][this.getY() + 1] instanceof PutCell) {
                if(Mapa.matrix[this.getX()][this.getY()+1].element != null){
                    synchronized (this){
                        Mapa.matrix[this.getX()][this.getY()+1].vozilaWait.add(this);
                        wait();
                    }
                }
                if(Mapa.matrix[this.getX()][this.getY()+1].element == null) {
                    Mapa.setElement(Mapa.matrix[this.getX()][this.getY()], null);
                    this.incrementY();
                    Mapa.setElement(Mapa.matrix[this.getX()][this.getY()], this);
                }
            }
        }
    }
    private void kretanjePut1Smijer1() throws InterruptedException {
        while (this.getY() > 20) {
            spavaj();
            if (Mapa.matrix[this.getX()][this.getY() - 1] instanceof PutCell) {
                if(Mapa.matrix[this.getX()][this.getY()-1].element != null){
                    synchronized (this){
                        Mapa.matrix[this.getX()][this.getY()-1].vozilaWait.add(this);
                        wait();
                    }
                }
                if(Mapa.matrix[this.getX()][this.getY()-1].element == null) {
                    Mapa.setElement(Mapa.matrix[this.getX()][this.getY()], null);
                    this.decrementY();
                    Mapa.setElement(Mapa.matrix[this.getX()][this.getY()],this);
                }
            }
        }
        while (true) {
            spavaj();
            if (this.getX() == 0) {
                Mapa.setElement(Mapa.matrix[this.getX()][this.getY()], null);
                break;
            }
            if (Mapa.matrix[this.getX() - 1][this.getY()] instanceof PutCell) {
                if(Mapa.matrix[this.getX() - 1][this.getY()].element != null){
                    synchronized (this){
                        Mapa.matrix[this.getX() - 1][this.getY()].vozilaWait.add(this);
                        wait();
                    }
                }
                if(Mapa.matrix[this.getX() - 1][this.getY()].element == null) {
                    Mapa.setElement(Mapa.matrix[this.getX()][this.getY()], null);
                    this.decrementX();
                    Mapa.setElement(Mapa.matrix[this.getX()][this.getY()], this);
                }
            } else if (Mapa.matrix[this.getX() - 1][this.getY()] instanceof PruzniPrelaz) {
                if(Mapa.matrix[this.getX() - 1][this.getY()].getCounter().intValue() != 0){
                    synchronized (this) {
                        PruzniPrelaz.waitingListAB1.add(this);
                        wait();
                    }
                }
                if(Mapa.matrix[this.getX() - 1][this.getY()].element == null) {
                    Mapa.setElement(Mapa.matrix[this.getX()][this.getY()], null);
                    this.decrementX();
                    Mapa.setElement(Mapa.matrix[this.getX()][this.getY()], this);
                }
            }
        }
    }
    private void kretanjePut2Smijer0() throws InterruptedException {
        while (true) {
            spavaj();
            if (this.getY() == 29) {
                Mapa.setElement(Mapa.matrix[this.getX()][this.getY()], null);
                break;
            }
            if (Mapa.matrix[this.getX()][this.getY() + 1] instanceof PutCell) {
                if(Mapa.matrix[this.getX()][this.getY()+1].element != null){
                    synchronized (this) {
                        Mapa.matrix[this.getX()][this.getY()+1].vozilaWait.add(this);
                        wait();
                    }
                }
                if(Mapa.matrix[this.getX()][this.getY()+1].element == null) {
                    Mapa.setElement(Mapa.matrix[this.getX()][this.getY()], null);
                    this.incrementY();
                    Mapa.setElement(Mapa.matrix[this.getX()][this.getY()], this);
                }
            } else if (Mapa.matrix[this.getX()][this.getY() + 1] instanceof PruzniPrelaz) {
                if(Mapa.matrix[this.getX()][this.getY() + 1].getCounter().intValue() != 0){
                    synchronized (this){
                        PruzniPrelaz.waitingListBC0.add(this);
                        wait();
                    }
                }
                if(Mapa.matrix[this.getX()][this.getY()+1].element == null) {
                    Mapa.setElement(Mapa.matrix[this.getX()][this.getY()], null);
                    this.incrementY();
                    Mapa.setElement(Mapa.matrix[this.getX()][this.getY()], this);
                }
            }
        }
    }
    private void kretanjePut2Smijer1() throws InterruptedException {
        while (true) {
            spavaj();
            if (this.getY() == 0) {
                Mapa.setElement(Mapa.matrix[this.getX()][this.getY()], null);
                break;
            }
            if (Mapa.matrix[this.getX()][this.getY() - 1] instanceof PutCell) {
                if(Mapa.matrix[this.getX()][this.getY()-1].element != null){
                    synchronized (this){
                        Mapa.matrix[this.getX()][this.getY()-1].vozilaWait.add(this);
                        wait();
                    }
                }
                if(Mapa.matrix[this.getX()][this.getY()-1].element == null) {
                    Mapa.setElement(Mapa.matrix[this.getX()][this.getY()], null);
                    this.decrementY();
                    Mapa.setElement(Mapa.matrix[this.getX()][this.getY()], this);
                }
            } else if (Mapa.matrix[this.getX()][this.getY() - 1] instanceof PruzniPrelaz) {
                if(Mapa.matrix[this.getX()][this.getY() - 1].getCounter().intValue() != 0){
                    synchronized (this){
                        PruzniPrelaz.waitingListBC1.add(this);
                        wait();
                    }
                }
                if(Mapa.matrix[this.getX()][this.getY()-1].element == null) {
                    Mapa.setElement(Mapa.matrix[this.getX()][this.getY()], null);
                    this.decrementY();
                    Mapa.setElement(Mapa.matrix[this.getX()][this.getY()], this);
                }

            }
        }
    }
    private void kretanjePut3Smijer0() throws InterruptedException {
        while (this.getX() > 21) {
            spavaj();
            if (Mapa.matrix[this.getX() - 1][this.getY()] instanceof PutCell) {
                if(Mapa.matrix[this.getX() - 1][this.getY()].element != null){
                    synchronized (this){
                        Mapa.matrix[this.getX() - 1][this.getY()].vozilaWait.add(this);
                        wait();
                    }
                }
                if(Mapa.matrix[this.getX() - 1][this.getY()].element == null) {
                    Mapa.setElement(Mapa.matrix[this.getX()][this.getY()], null);
                    this.decrementX();
                    Mapa.setElement(Mapa.matrix[this.getX()][this.getY()], this);
                }
            } else if (Mapa.matrix[this.getX() - 1][this.getY()] instanceof PruzniPrelaz) {
                if( Mapa.matrix[this.getX() - 1][this.getY()].getCounter().intValue() != 0){
                    synchronized (this){
                        PruzniPrelaz.waitingListCE0.add(this);
                        wait();
                    }
                }
                if(Mapa.matrix[this.getX() - 1][this.getY()].element == null) {
                    Mapa.setElement(Mapa.matrix[this.getX()][this.getY()], null);
                    this.decrementX();
                    Mapa.setElement(Mapa.matrix[this.getX()][this.getY()], this);
                }
            }
        }
        while (true) {
            spavaj();
            if (this.getY() == 29) {
                Mapa.setElement(Mapa.matrix[this.getX()][this.getY()], null);
                break;
            }
            if (Mapa.matrix[this.getX()][this.getY() + 1] instanceof PutCell) {
                if(Mapa.matrix[this.getX()][this.getY()+1].element != null){
                    synchronized (this){
                        Mapa.matrix[this.getX()][this.getY()+1].vozilaWait.add(this);
                        wait();
                    }
                }
                if(Mapa.matrix[this.getX()][this.getY()+1].element == null) {
                    Mapa.setElement(Mapa.matrix[this.getX()][this.getY()], null);
                    this.incrementY();
                    Mapa.setElement(Mapa.matrix[this.getX()][this.getY()], this);
                }
            }
        }
    }
    private  void kretanjePut3Smijer1() throws InterruptedException {
        while (this.getY() > 21) {
            spavaj();
            if (Mapa.matrix[this.getX()][this.getY() - 1] instanceof PutCell) {
                if(Mapa.matrix[this.getX()][this.getY()-1].element != null){
                    synchronized (this){
                        Mapa.matrix[this.getX()][this.getY()-1].vozilaWait.add(this);
                        wait();
                    }
                }
                if(Mapa.matrix[this.getX()][this.getY()-1].element == null) {
                    Mapa.setElement(Mapa.matrix[this.getX()][this.getY()], null);
                    this.decrementY();
                    Mapa.setElement(Mapa.matrix[this.getX()][this.getY()], this);
                }
            }
        }
        while (true) {
            spavaj();
            if (this.getX() == 29) {
                Mapa.setElement(Mapa.matrix[this.getX()][this.getY()], null);
                break;
            }
            if (Mapa.matrix[this.getX() + 1][this.getY()] instanceof PutCell) {
                if(Mapa.matrix[this.getX() + 1][this.getY()].element != null){
                    synchronized (this){
                        Mapa.matrix[this.getX() + 1][this.getY()].vozilaWait.add(this);
                        wait();
                    }
                }
                if(Mapa.matrix[this.getX() + 1][this.getY()].element == null) {
                    Mapa.setElement(Mapa.matrix[this.getX()][this.getY()], null);
                    this.incrementX();
                    Mapa.setElement(Mapa.matrix[this.getX()][this.getY()], this);
                }
            } else if (Mapa.matrix[this.getX() + 1][this.getY()] instanceof PruzniPrelaz) {
                if(Mapa.matrix[this.getX() + 1][this.getY()].getCounter().intValue() != 0){
                    synchronized (this){
                        PruzniPrelaz.waitingListCE1.add(this);
                        wait();
                    }
                }
                if(Mapa.matrix[this.getX() + 1][this.getY()].element == null) {
                    Mapa.setElement(Mapa.matrix[this.getX()][this.getY()], null);
                    this.incrementX();
                    Mapa.setElement(Mapa.matrix[this.getX()][this.getY()], this);
                }
            }
        }
    }

    public void spavaj(){
        try {
            Thread.sleep(this.brzina);
        } catch (InterruptedException e) {
            Logger.getLogger(Logger.GLOBAL_LOGGER_NAME).log(Level.WARNING, e.fillInStackTrace().toString());
        }
    }

}
