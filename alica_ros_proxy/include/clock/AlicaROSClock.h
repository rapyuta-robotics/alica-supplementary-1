#pragma once

#include <engine/AlicaClock.h>
#include <engine/AlicaEngine.h>
#include "ros/ros.h"


namespace alicaRosProxy
{

class AlicaROSClock : public alica::AlicaClock
{
public:
    AlicaROSClock(alica::AlicaEngine* ae);
    virtual ~AlicaROSClock() {};
    alica::AlicaTime now() const override;
    void sleep(const alica::AlicaTime&) const override;
};

} /* namespace alicaRosProxy */
