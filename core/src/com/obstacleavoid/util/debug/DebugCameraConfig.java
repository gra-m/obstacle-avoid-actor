package com.obstacleavoid.util.debug;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.Logger;

import static com.obstacleavoid.util.Common.LOCALE;


public class DebugCameraConfig
{
    // == constants ==
    // json names
    private static final String LEFT_KEY = "leftKey";
    private static final String RIGHT_KEY = "rightKey";
    private static final String UP_KEY = "upKey";
    private static final String DOWN_KEY = "downKey";

    private static final String ZOOM_IN_KEY = "zoomInKey";
    private static final String ZOOM_OUT_KEY = "zoomOutKey";
    private static final String RESET_KEY = "resetKey";
    private static final String LOG_KEY = "logKey";

    private static final String MOVE_SPEED = "moveSpeed";
    private static final String ZOOM_SPEED = "zoomSpeed";
    private static final String MAX_ZOOM_IN = "maxZoomIn";
    private static final String MAX_ZOOM_OUT = "maxZoomOut";
    private static final String ZOOM_LEVEL = "zoomLevel";

    // default keys
    private static final int DEFAULT_LEFT_KEY = Input.Keys.A;
    private static final int DEFAULT_RIGHT_KEY = Input.Keys.D;
    private static final int DEFAULT_UP_KEY = Input.Keys.W;
    private static final int DEFAULT_DOWN_KEY = Input.Keys.S;

    private static final int DEFAULT_ZOOM_IN_KEY = Input.Keys.COMMA;
    private static final int DEFAULT_ZOOM_OUT_KEY = Input.Keys.PERIOD;
    private static final int DEFAULT_RESET_KEY = Input.Keys.BACKSPACE;
    private static final int DEFAULT_LOG_KEY = Input.Keys.ENTER;

    private static final float DEFAULT_MOVE_SPEED = 20.0f;
    private static final float DEFAULT_ZOOM_SPEED = 2.0f;
    private static final float DEFAULT_MAX_ZOOM_IN = 0.2f;
    private static final float DEFAULT_MAX_ZOOM_OUT = 30.0f;
    private static final float DEFAULT_ZOOM_LEVEL = 1.0f;


    // == set value attributes, either from file or default ==
    private int leftKey;
    private int rightKey;
    private int upKey;
    private int downKey;

    private int zoomInKey;
    private int zoomOutKey;
    private int resetKey;
    private int logKey;

    private float moveSpeed;
    private float zoomSpeed;
    private float maxZoomIn;
    private float maxZoomOut;
    private float zoomLevel;

    // import and logging
    private static final Logger LOG = new Logger( DebugCameraController.class.getName( ), Logger.DEBUG );
    private static final String FILE_PATH = "debug/debugCameraConfig.json";
    private FileHandle fileHandle;

    public DebugCameraConfig( )
    {
        init( );
    }

    private void init( )
    {
        fileHandle = Gdx.files.internal( FILE_PATH );

        if ( fileHandle.exists( ) ) {
            load( );
        } else {
            LOG.info( "Using defaults. File does not exist " + FILE_PATH );
            setUpDefaults( );
        }
    }


    private void load( )
    {
        try {
            JsonReader jsonReader = new JsonReader( );
            JsonValue root = jsonReader.parse( fileHandle );
            leftKey = getInputKeyValue( root, LEFT_KEY, DEFAULT_LEFT_KEY );
            rightKey = getInputKeyValue( root, RIGHT_KEY, DEFAULT_RIGHT_KEY );
            upKey = getInputKeyValue( root, UP_KEY, DEFAULT_UP_KEY );
            downKey = getInputKeyValue( root, DOWN_KEY, DEFAULT_DOWN_KEY );

            maxZoomIn = root.getFloat( MAX_ZOOM_IN, DEFAULT_MAX_ZOOM_IN );
            maxZoomOut = root.getFloat( MAX_ZOOM_OUT, DEFAULT_MAX_ZOOM_OUT );
            resetKey = getInputKeyValue( root, RESET_KEY, DEFAULT_RESET_KEY );
            logKey = getInputKeyValue( root, LOG_KEY, DEFAULT_LOG_KEY );

            moveSpeed = root.getFloat( MOVE_SPEED, DEFAULT_MOVE_SPEED );
            zoomSpeed = root.getFloat( ZOOM_SPEED, DEFAULT_ZOOM_SPEED );
            zoomInKey = getInputKeyValue( root, ZOOM_IN_KEY, DEFAULT_ZOOM_IN_KEY );
            zoomOutKey = getInputKeyValue( root, ZOOM_OUT_KEY, DEFAULT_ZOOM_OUT_KEY );
            zoomLevel = root.getFloat( ZOOM_LEVEL, DEFAULT_ZOOM_LEVEL );

        } catch ( Exception e ) {
            LOG.info( String.format( "There was an error loading file %s... [Loading ALL defaults].\n%s", FILE_PATH,
                    e ) );
            setUpDefaults( );
        }

    }

    private static int getInputKeyValue( JsonValue root, String name, int defaultInputKey )
    {
        boolean validKey = true;
        String valueString = root.getString( name, Input.Keys.toString( defaultInputKey ) );

        if ( Input.Keys.valueOf( valueString ) == -1 ) {
            validKey = false;
            LOG.info( String.format( LOCALE, "[Loading SINGLE default]: Key '%s' had an invalid value allotted to it " +
                            "and is being replaced with the default input value for this key '%s'", name,
                    Input.Keys.toString( defaultInputKey ) ) );
        }

        return ( validKey ) ? Input.Keys.valueOf( valueString ) : defaultInputKey;
    }

    private void setUpDefaults( )
    {
        upKey = DEFAULT_UP_KEY;
        downKey = DEFAULT_DOWN_KEY;
        leftKey = DEFAULT_LEFT_KEY;
        rightKey = DEFAULT_RIGHT_KEY;

        maxZoomIn = DEFAULT_MAX_ZOOM_IN;
        maxZoomOut = DEFAULT_MAX_ZOOM_OUT;
        resetKey = DEFAULT_RESET_KEY;
        logKey = DEFAULT_LOG_KEY;

        moveSpeed = DEFAULT_MOVE_SPEED;
        zoomSpeed = DEFAULT_ZOOM_SPEED;
        zoomInKey = DEFAULT_ZOOM_IN_KEY;
        zoomOutKey = DEFAULT_ZOOM_OUT_KEY;
        zoomLevel = DEFAULT_ZOOM_LEVEL;
    }

    public boolean isUpPressed( )
    {
        return Gdx.input.isKeyPressed( upKey );
    }

    public boolean isDownPressed( )
    {
        return Gdx.input.isKeyPressed( downKey );
    }

    public boolean isLeftPressed( )
    {
        return Gdx.input.isKeyPressed( leftKey );
    }

    public boolean isRightPressed( )
    {
        return Gdx.input.isKeyPressed( rightKey );
    }

    public boolean isZoomInPressed( )
    {
        return Gdx.input.isKeyPressed( zoomInKey );
    }

    public boolean isZoomOutPressed( )
    {
        return Gdx.input.isKeyPressed( zoomOutKey );
    }

    public boolean isResetPressed( )
    {
        return Gdx.input.isKeyPressed( resetKey );
    }

    public boolean isLogPressed( )
    {
        return Gdx.input.isKeyPressed( logKey );
    }

    public float getMaxZoomIn( )
    {
        return maxZoomIn;
    }

    public float getMaxZoomOut( )
    {
        return maxZoomOut;
    }

    public float getMoveSpeed( )
    {
        return moveSpeed;
    }

    public float getZoomSpeed( )
    {
        return zoomSpeed;
    }

    public float getZoomLevel( ) { return zoomLevel; }

    @Override
    public String toString( )
    {
        try {
            String LS = System.getProperty( "line.separator" );
            return String.format( LOCALE, " DebugCameraConfig:%sleftKey = %s%srightKey = %s%supKey = %s%sdownKey = " +
                            "%s%szoomInKey = %s%szoomOutKey = %s%sresetKey = %s%slogKey = %s%smaxZoomIn = %f%s" +
                            "maxZoomOut = %f%smoveSpeed = %f%szoomSpeed = %f%szoomLevel = %f"
                    , LS, Input.Keys.toString( leftKey ), LS,
                    Input.Keys.toString( rightKey ), LS, Input.Keys.toString( upKey ), LS,
                    Input.Keys.toString( downKey ), LS,
                    Input.Keys.toString( zoomInKey ), LS, Input.Keys.toString( zoomOutKey ), LS,
                    Input.Keys.toString( resetKey ),
                    LS, Input.Keys.toString( logKey ), LS, maxZoomIn, LS, maxZoomOut, LS, moveSpeed, LS, zoomSpeed, LS,
                    zoomLevel );
        } catch ( IllegalArgumentException e ) {
            LOG.info( "@ config To String Illegal Argument exception probably caused by -1 on a call to Input.Keys" +
                    ".toString()", e );
        } catch ( Exception e ) {
            LOG.info( "@ config toString()", e );
        }
        return "Issue with config ToString(), the keys have not been allotted properly defaults were not implemented," +
                " " +
                "expect bugs, call to defaults to solve?";

    }
}
